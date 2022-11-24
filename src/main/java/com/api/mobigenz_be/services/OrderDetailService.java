package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.OrderDetailDto;
import com.api.mobigenz_be.entities.OrderDetail;

public interface OrderDetailService {

    boolean deleteOrderDetail(Integer id);

    OrderDetailDto addOrderDetailToOrder(Integer order_id, OrderDetailDto orderDetailDto);

     OrderDetailDto mapOrderDetailToOrderDetailDTO(OrderDetail orderDetail);
}
