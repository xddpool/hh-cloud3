package com.hh.jushuitan.service;

import com.hh.jushuitan.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gx
 * @version 1.0.0
 * @since 7/1/23
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderItemTask {

    private final OrderItemService orderItemService;

//    @Scheduled(cron = "0 10 0 * * *")
    public void fetchItemData() {
        log.info("开始拉取订单");
        List<OrderItem> orderItems = orderItemService.fetchOrders(null);
        log.info("开始拉取订单项目{}条", orderItems.size());
        try {
            orderItemService.saveOrders(orderItems);
            log.info("入库订单项目{}条", orderItems.size());
        } catch (Exception e) {
            log.error("拉取订单失败，请手动执行!");
        }
    }
}
