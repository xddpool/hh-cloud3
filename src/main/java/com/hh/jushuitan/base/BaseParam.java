package com.hh.jushuitan.base;

import lombok.Data;

/**
 * @author gx
 * @version 1.0.0
 * @since 7/1/23
 **/
@Data
public class BaseParam {
    private static final long serialVersionUID = 4062928190889546527L;

    /**
     * 搜索值
     */
    private String searchValue;

    /**
     * 开始时间
     */
    private String searchBeginTime;

    /**
     * 结束时间
     */
    private String searchEndTime;

    /**
     * 分页
     */
    private Integer pageIndex = 1;
    /**
     * 每页记录
     */
    private Integer pageSize = 25;

}
