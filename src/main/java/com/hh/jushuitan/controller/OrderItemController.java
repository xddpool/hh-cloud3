package com.hh.jushuitan.controller;

import com.hh.jushuitan.base.PageResult;
import com.hh.jushuitan.base.ResponseVO;
import com.hh.jushuitan.param.query.OrderItemQueryParam;
import com.hh.jushuitan.service.OrderItemService;
import com.hh.jushuitan.vo.OrderItemVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author gx
 * @version 1.0.0
 * @since 7/1/23
 **/
@RestController
@RequestMapping("orderItem")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping("fetch")
    public ResponseVO<?> fetchData(@RequestParam String time) {
        orderItemService.fetchAndSaveOrders(time);
        return ResponseVO.success();
    }

    @GetMapping("page")
    public ResponseVO<PageResult<OrderItemVO>> page(OrderItemQueryParam orderItemQueryParam) {
        return ResponseVO.success(orderItemService.pageOrderItem(orderItemQueryParam));
    }
}
