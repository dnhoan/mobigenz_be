package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.*;
import com.api.mobigenz_be.entities.*;
import com.api.mobigenz_be.repositories.OrderDetailRepository;
import com.api.mobigenz_be.repositories.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public boolean saveOrder(OrderDto orderDto) {

        Order order = Order
                .builder()
                .address(orderDto.getAddress())
                .carrier(orderDto.getCarrier())
                .checkout(orderDto.getCheckout())
                .id(orderDto.getId())
                .orderStatus(orderDto.getOrderStatus())
                .customer(this.modelMapper.map(orderDto.getCustomerDTO(), Customer.class))
                .goodsValue(orderDto.getGoodsValue())
                .checkout(orderDto.getCheckout())
                .note(orderDto.getNote())
                .payStatus(orderDto.getPayStatus())
                .quantity(orderDto.getQuantity())
                .recipientEmail(orderDto.getRecipientEmail())
                .recipientName(orderDto.getRecipientName())
                .recipientPhone(orderDto.getRecipientPhone())
                .shipFee(orderDto.getShipFee())
                .totalMoney(orderDto.getTotalMoney())
                .shipDate(orderDto.getShipDate())
                .address(orderDto.getAddress())
                .ctime(LocalDateTime.now())
//                .transaction(new Transaction())w
                .build();
        order = this.orderRepository.saveAndFlush(order);
        Order finalOrder = order;
        List<OrderDetail> orderDetailList = orderDto.getOrderDetailDtos().stream().map(orderDetailDto -> {
                    ProductDetail productDetail = ProductDetail
                            .builder()
                            .id(orderDetailDto.getProductDetailDto().getId())
                            .build();
                    return OrderDetail
                            .builder()
                            .productDetail(productDetail)
                            .order(finalOrder)
                            .amount(orderDetailDto.getAmount())
                            .id(orderDetailDto.getId())
                            .ctime(LocalDateTime.now())
                            .priceSell(orderDetailDto.getPriceSell())
                            .productPrice(orderDetailDto.getProductPrice())
                            .note(orderDetailDto.getNote())
                            .build();
                }
        ).collect(Collectors.toList());
        this.orderDetailRepository.saveAll(orderDetailList);
        return true;
    }

    @Override
    public OrderDto getOrderById(Integer order_id) {
        Optional<Order> orderOptional = this.orderRepository.findById(order_id);
        OrderDto orderDto = new OrderDto();
        return orderOptional.map(this::mapOrderToCustomerOrderDto).orElse(orderDto);
    }

    @Override
    public List<OrderDto> getOrdersByCustomerId(Integer customer_id) {
        List<Order> orders = this.orderRepository.getOrdersByCustomerId(customer_id);
        return orders.stream().map(this::mapOrderToCustomerOrderDto).collect(Collectors.toList());
    }

    private OrderDto mapOrderToCustomerOrderDto(Order order) {
        OrderDto orderDto = this.modelMapper.map(order, OrderDto.class);
        orderDto.setCustomerDTO(this.modelMapper.map(order.getCustomer(), CustomerDTO.class));
        List<OrderDetailDto> orderDetailDtos = this.orderDetailRepository.getOrderDetailByOrderId(order.getId())
                .stream()
                .map(orderDetail -> {
                            OrderDetailDto orderDetailDto = this.modelMapper.map(orderDetail, OrderDetailDto.class);
                            orderDetailDto.setProductDetailDto(this.modelMapper.map(orderDetail.getProductDetail(), ProductDetailDto.class));
                            return orderDetailDto;
                        }
                ).collect(Collectors.toList());
        orderDto.setOrderDetailDtos(orderDetailDtos);
        return orderDto;
    }

}
