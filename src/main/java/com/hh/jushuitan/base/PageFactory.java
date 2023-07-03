package com.hh.jushuitan.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class PageFactory {
    /**
     * 每页大小（默认20）
     */
    private static final String PAGE_SIZE_PARAM_NAME = "pageSize";

    /**
     * 第几页（从1开始）
     */
    private static final String PAGE_NO_PARAM_NAME = "pageIndex";

    /**
     * 默认分页，在使用时PageFactory.defaultPage会自动获取pageSize和pageNo参数
     *
     * @author changhong
     * @date 2020/3/30 16:42
     */
    public static <T> Page<T> defaultPage() {

        int pageSize = 20;
        int pageNo = 1;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new RuntimeException("当前请求参数为空或数据缺失，请联系管理员");
        }
        HttpServletRequest request = requestAttributes.getRequest();
        //每页条数
        String pageSizeString = request.getParameter(PAGE_SIZE_PARAM_NAME);
        if (ObjectUtil.isNotEmpty(pageSizeString)) {
            pageSize = Integer.parseInt(pageSizeString);
        }

        //第几页
        String pageNoString = request.getParameter(PAGE_NO_PARAM_NAME);
        if (ObjectUtil.isNotEmpty(pageNoString)) {
            pageNo = Integer.parseInt(pageNoString);
        }

        return new Page<>(pageNo, pageSize);
    }

    public static <T> PageResult<T> buildPageResult(Page<?> page, Class<T> clazz) {
        PageResult<T> result = new PageResult<>(page.getCurrent(), page.getSize(), page.getPages(), page.getTotal(), null);
        if (page.getTotal() > 0) {
            List<T> vos = page.getRecords().stream().map(r -> BeanUtil.toBean(r, clazz)).collect(Collectors.toList());
            result.setList(vos);
        }
        return result;
    }
}

