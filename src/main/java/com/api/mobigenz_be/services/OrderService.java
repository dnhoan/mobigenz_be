package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.OrderDto;
import com.api.mobigenz_be.DTOs.UserOrderDto;

import java.util.List;

public interface OrderService {

    boolean saveOrder(OrderDto orderDto);

    OrderDto getOrderById(Integer order_id);

    List<OrderDto> getOrdersByCustomerId(Integer customer_id);
}
