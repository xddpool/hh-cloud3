package com.hh.jushuitan.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hh.jushuitan.entity.OrderItem;
import com.hh.jushuitan.vo.OrderItemVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author gx
 * @version 1.0.0
 * @since 7/1/23
 **/
@Mapper(componentModel = "spring")
public interface OrderItemConvert {
    OrderItemConvert INSTANCE = Mappers.getMapper(OrderItemConvert.class);

    OrderItemVO toOrderItemVO(OrderItem orderItem);

    Page<OrderItemVO> toOrderItemVOPage(Page<OrderItem> orderItemPage);
}
