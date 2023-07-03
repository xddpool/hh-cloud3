package com.hh.jushuitan.param;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gx
 * @version 1.0.0
 * @since 7/1/23
 **/
@Data
public class OrderItemParam {

    private Integer orderId;

    private LocalDateTime orderDate;

    private String shopStatus;

    private String questionType;

    private String shopId;

    private String questionDesc;

    private String soId;

    private String status;

    private String receiverState;

    private String receiverCity;

    private String receiverDistrict;

    private LocalDateTime sendDate;

    private LocalDateTime planDeliveryDate;

    private String creatorName;

    private String buyerTaxNo;

    private String invoiceType;

    private BigDecimal payAmount;

    private BigDecimal freight;

    private String buyerMessage;

    private String remark;

    private String invoiceTitle;

    private Boolean isCod;

    private String type;

    private BigDecimal paidAmount;

    private LocalDateTime payDate;

    private LocalDateTime modified;

    private String orderFrom;

    private String lId;

    private String shopName;

    private String wmsCoId;

    private String logisticsCompany;

    private BigDecimal freeAmount;

    private String coId;

    private String drpCoIdTo;

    private LocalDateTime endTime;

    private String referrerId;

    private String invoiceData;

    private String referrerName;

    private String drpCoIdFrom;

    private BigDecimal weight;

    private BigDecimal fWeight;

    private Boolean isSplit;

    private Boolean isMerge;

    private String oId;

    private String labels;

    private String currency;

    private String lcId;

    private List<Item> items;

    @Data
    public static class Item {

        private Long oiId;

        private String referrerId;

        private String skuType;

        private BigDecimal itemPayAmount;

        private BigDecimal price;

        private String outerOiId;

        private Boolean isGift;

        private String refundStatus;

        private String refundId;

        private String itemStatus;

        private String iId;

        private String shopIId;

        private String rawSoId;

        private Boolean isPresale;

        private String propertiesValue;

        private BigDecimal amount;

        private BigDecimal basePrice;

        private Integer qty;

        private String name;

        private String skuId;

        private String shopSkuId;

        private String batchId;

        private LocalDateTime producedDate;

        private BigDecimal buyerPaidAmount;

        private BigDecimal sellerIncomeAmount;

        private String lwhId;

        private String lwhName;
    }
}
