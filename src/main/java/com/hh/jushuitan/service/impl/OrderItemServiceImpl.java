package com.hh.jushuitan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.jushuitan.base.PageFactory;
import com.hh.jushuitan.base.PageResult;
import com.hh.jushuitan.base.PageResultFactory;
import com.hh.jushuitan.convert.OrderItemConvert;
import com.hh.jushuitan.entity.OrderItem;
import com.hh.jushuitan.mapper.OrderItemMapper;
import com.hh.jushuitan.param.OrderItemParam;
import com.hh.jushuitan.param.query.OrderItemQueryParam;
import com.hh.jushuitan.service.OrderItemService;
import com.hh.jushuitan.vo.OrderItemVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * @author gx
 * @version 1.0.0
 * @since 7/1/23
 **/
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

    private static final String SIGN_METHOD_MD5 = "md5";
    private static final String SIGN_METHOD_HMAC = "hmac";
    private static final String CHARSET_UTF8 = "utf-8";
    private static final String CONTENT_ENCODING_GZIP = "gzip";

    // TOP服务地址，正式环境需要设置为http://gw.api.taobao.com/router/rest
    private static final String serverUrl = "https://a1q40taq0j.api.taobao.com/router/qm";
    private static final String appKey = "31224063";
    // 可替换为您的应用的appKey
    private static final String appSecret = "42403bd60308cf19c7bbe3d489048da4";
    // 可替换为您的应用的appSecret


    @Override
    public List<OrderItem> fetchOrders(String time) {
        int pageIndex = 1;
        LocalDateTime now = LocalDateTime.now().minusDays(1);
        if (StrUtil.isNotBlank(time)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(time, formatter);
            now = date.atStartOfDay();
        }
        LocalDateTime startOfDay = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = now.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startDatetime = startOfDay.format(formatter);
        String endDatetime = endOfDay.format(formatter);

        List<OrderItem> orderItemList = new ArrayList<>();
        while (true) {
            Map<String, String> params = new HashMap<>();
            // 公共参数
            params.put("method", "jushuitan.order.list.query");
            params.put("customer_id", "10003483");
            params.put("target_app_key", "23060081");
            params.put("app_key", appKey);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            params.put("timestamp", df.format(new Date()));
            params.put("format", "json");
            params.put("v", "2.0");
            params.put("sign_method", "hmac");
            // 业务参数
            params.put("page_index", String.valueOf(pageIndex));
            params.put("page_size", "25");
            params.put("start_time", startDatetime);
            params.put("end_time", endDatetime);
            params.put("date_type", "2");
            // 签名参数
            try {
                params.put("sign", signTopRequest(params, appSecret, SIGN_METHOD_HMAC));
            } catch (IOException e) {
                throw new RuntimeException("签名失败!", e);
            }
            // 请用API
            String json;
            try {
                json = callApi(new URL(serverUrl), params);
            } catch (IOException e) {
                throw new RuntimeException("解析server url 失败!", e);
            }
            JSONObject jsonObject = JSONObject.parseObject(json);
            JSONObject jsonRes = jsonObject.getJSONObject("response");
            Boolean hasNext = jsonRes.getBoolean("has_next");
            if (!hasNext) {
                break;
            }
            pageIndex++;
            JSONArray jsonArray = jsonRes.getJSONArray("orders");

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject orderObject = jsonArray.getJSONObject(i);
                OrderItemParam orderItemParam = JSON.toJavaObject(orderObject, OrderItemParam.class);
                for (OrderItemParam.Item item : orderItemParam.getItems()) {
                    OrderItem orderItem = new OrderItem();
                    BeanUtil.copyProperties(item, orderItem, false);
                    BeanUtil.copyProperties(orderItemParam, orderItem, false);
                    orderItemList.add(orderItem);
                }
            }
        }
        return orderItemList;
    }

    /**
     * 保存并且更新订单状态
     * @param orderItemList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrders(List<OrderItem> orderItemList) {
        saveOrUpdateBatch(orderItemList);
    }

    /**
     *拉取并保存订单，通过 fetchOrders 拿到解析后的 orderItems 直接进行存储
     * @param time
     */
    @Override
    public void fetchAndSaveOrders(String time) {
        List<OrderItem> orderItems = fetchOrders(time);
        saveOrders(orderItems);
    }

    @Override
    public PageResult<OrderItemVO> pageOrderItem(OrderItemQueryParam orderItemQueryParam) {
        LambdaQueryWrapper<OrderItem> wrappers = Wrappers.<OrderItem>lambdaQuery()
                .like(StrUtil.isNotBlank(orderItemQueryParam.getSearchValue()), OrderItem::getName, orderItemQueryParam.getSearchValue());
        if (StrUtil.isNotBlank(orderItemQueryParam.getSearchBeginTime())) {
            wrappers.ge(StrUtil.isNotBlank(orderItemQueryParam.getSearchBeginTime()), OrderItem::getOrderDate,
                    LocalDate.parse(orderItemQueryParam.getSearchBeginTime()).atStartOfDay());
        }
        if (StrUtil.isNotBlank(orderItemQueryParam.getSearchEndTime())) {
            wrappers.le(StrUtil.isNotBlank(orderItemQueryParam.getSearchEndTime()), OrderItem::getOrderDate,
                    LocalDate.parse(orderItemQueryParam.getSearchEndTime()).atTime(LocalTime.MAX));
        }

        Page<OrderItem> page = page(PageFactory.defaultPage(), wrappers);
        Page<OrderItemVO> orderItemVOPage = OrderItemConvert.INSTANCE.toOrderItemVOPage(page);
        return PageResultFactory.defaultPageResult(orderItemVOPage);
    }

    /**
     * 对TOP请求进行签名。
     */
    private String signTopRequest(Map<String, String> params, String secret, String signMethod) throws IOException {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        if (SIGN_METHOD_MD5.equals(signMethod)) {
            query.append(secret);
        }
        for (String key : keys) {
            String value = params.get(key);
            if (isNotEmpty(key) && isNotEmpty(value)) {
                query.append(key).append(value);
            }
        }

        // 第三步：使用MD5/HMAC加密
        byte[] bytes;
        if (SIGN_METHOD_HMAC.equals(signMethod)) {
            bytes = encryptHMAC(query.toString(), secret);
        } else {
            query.append(secret);
            bytes = encryptMD5(query.toString());
        }

        // 第四步：把二进制转化为大写的十六进制
        return byte2hex(bytes);
    }

    /**
     * 对字节流进行HMAC_MD5摘要。
     */
    private byte[] encryptHMAC(String data, String secret) throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(CHARSET_UTF8), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes(CHARSET_UTF8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    /**
     * 对字符串采用UTF-8编码后，用MD5进行摘要。
     */
    private byte[] encryptMD5(String data) throws IOException {
        return encryptMD5(data.getBytes(CHARSET_UTF8));
    }

    /**
     * 对字节流进行MD5摘要。
     */
    private byte[] encryptMD5(byte[] data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data);
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    /**
     * 把字节流转换为十六进制表示方式。
     */
    private String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    private String callApi(URL url, Map<String, String> params) throws IOException {
        String query = buildQuery(params, CHARSET_UTF8);
        byte[] content = {};
        if (query != null) {
            content = query.getBytes(CHARSET_UTF8);
        }

        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Host", url.getHost());
            conn.setRequestProperty("Accept", "text/xml,text/javascript");
            conn.setRequestProperty("User-Agent", "top-sdk-java");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + CHARSET_UTF8);
            out = conn.getOutputStream();
            out.write(content);
            rsp = getResponseAsString(conn);
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    private static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Map.Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (isNotEmpty(name) && isNotEmpty(value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }

                query.append(name).append("=").append(URLEncoder.encode(value, charset));
            }
        }

        return query.toString();
    }

    private String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        if (conn.getResponseCode() < 400) {
            String contentEncoding = conn.getContentEncoding();
            if (CONTENT_ENCODING_GZIP.equalsIgnoreCase(contentEncoding)) {
                return getStreamAsString(new GZIPInputStream(conn.getInputStream()), charset);
            } else {
                return getStreamAsString(conn.getInputStream(), charset);
            }
        } else {// Client Error 4xx and Server Error 5xx
            throw new IOException(conn.getResponseCode() + " " + conn.getResponseMessage());
        }
    }

    private String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            Reader reader = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();

            final char[] buff = new char[1024];
            int read = 0;
            while ((read = reader.read(buff)) > 0) {
                response.append(buff, 0, read);
            }

            return response.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private String getResponseCharset(String ctype) {
        String charset = CHARSET_UTF8;

        if (isNotEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (isNotEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }

        return charset;
    }

    private static boolean isNotEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return true;
            }
        }
        return false;
    }

}
