package com.hh.jushuitan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("order_item")
public class OrderItem {

    @TableId(value = "oi_id", type = IdType.INPUT)
    private Long oiId;


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


    private String node;


    private String shopSite;


    private String receiverZip;


    private String skus;


    private String unLid;


    private String receiverEmail;


    private LocalDateTime created;


    private String shipment;


    private LocalDateTime signTime;


    private String oaid;


    private String openId;


    private Integer ts;

    private BigDecimal buyerPaidAmount;

    private BigDecimal sellerIncomeAmount;


    private String chosenChannel;


    private String sellerFlag;


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

    private String lwhId;

    private String lwhName;
}