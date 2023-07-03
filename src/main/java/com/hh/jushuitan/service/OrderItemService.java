package com.hh.jushuitan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hh.jushuitan.base.PageResult;
import com.hh.jushuitan.entity.OrderItem;
import com.hh.jushuitan.param.query.OrderItemQueryParam;
import com.hh.jushuitan.vo.OrderItemVO;

import java.util.List;

/**
 * @author gx
 * @version 1.0.0
 * @since 7/1/23
 **/
public interface OrderItemService extends IService<OrderItem> {

    List<OrderItem> fetchOrders(String time);

    void saveOrders(List<OrderItem> orderItemList);

    void fetchAndSaveOrders(String time);

    PageResult<OrderItemVO> pageOrderItem(OrderItemQueryParam orderItemQueryParam);

}
