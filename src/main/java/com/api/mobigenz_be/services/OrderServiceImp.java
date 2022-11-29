package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.*;
import com.api.mobigenz_be.constants.Constant;
import com.api.mobigenz_be.entities.*;
import com.api.mobigenz_be.repositories.CartRepository;
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
    private CartRepository cartRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderDetailService orderDetailService;


    @Override
    @Transactional
    public OrderDto adminSaveOrder(OrderDto orderDto) {
        Customer customer = this.modelMapper.map(orderDto.getCustomerDTO(), Customer.class);
        Order order = this.modelMapper.map(orderDto, Order.class);
        order.setCustomer(customer);
        order = this.orderRepository.saveAndFlush(order);
        Order finalOrder = order;
        List<OrderDetail> orderDetails = orderDto.getOrderDetailDtos().stream().map(orderDetailDto -> {
            OrderDetail orderDetail = this.modelMapper.map(orderDetailDto, OrderDetail.class);
            List<Imei> imeis = orderDetailDto.getImeiDtoList().stream().map(imeiDto -> {
                Imei imei = this.modelMapper.map(imeiDto, Imei.class);
                imei.setOrderDetail(orderDetail);
                imei.setStatus(0);
                return imei;
            }).collect(Collectors.toList());
            ProductDetail productDetail = this.modelMapper.map(orderDetailDto.getProductDetailCartDto(), ProductDetail.class);
            orderDetail.setImeis(imeis);
            orderDetail.setOrder(finalOrder);
            orderDetail.setProductDetail(productDetail);
            return orderDetail;
        }).collect(Collectors.toList());
        this.orderDetailRepository.saveAll(orderDetails);
        return this.mapOrderToCustomerOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto saveOrder(OrderDto orderDto) {
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
        this.cartRepository.deleteCartByCustomerId(order.getCustomer().getId());
        Order finalOrder = order;
        List<OrderDetail> orderDetailList = orderDto.getOrderDetailDtos().stream().map(orderDetailDto -> {
                    ProductDetail productDetail = ProductDetail
                            .builder()
                            .id(orderDetailDto.getProductDetailCartDto().getId())
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
        return this.mapOrderToCustomerOrderDto(order);
    }

    @Override
    @Transactional
    public void cancelOrder(Integer order_id, String note) {
        this.orderRepository.updateOrderStatus(Constant.OrderStatus.CANCEL_ORDER, note, order_id);
    }

    @Override
    @Transactional
    public void updateOrderStatus(Integer order_id, Integer newStatus, String note) {
        this.orderRepository.updateOrderStatus(newStatus, note, order_id);
    }

    @Override
    @Transactional
    public OrderDto getOrderById(Integer order_id) {
        Optional<Order> orderOptional = this.orderRepository.findById(order_id);
        OrderDto orderDto = new OrderDto();
        return orderOptional.map(this::mapOrderToCustomerOrderDto).orElse(orderDto);
    }

    @Override
    @Transactional
    public List<OrderDto> getOrdersByCustomerId(Integer customer_id) {
        List<Order> orders = this.orderRepository.getOrdersByCustomerIdOrderByCtimeDesc(customer_id);
        return orders.stream().map(this::mapOrderToCustomerOrderDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<OrderDto> getOrdersByOrderStatus(Integer orderStatus) {
        List<Order> orders = this.orderRepository.getOrderByOrderStatus(orderStatus);
        return orders.stream().map(this::mapOrderToCustomerOrderDto).collect(Collectors.toList());
    }

    private OrderDto mapOrderToCustomerOrderDto(Order order) {
        OrderDto orderDto = this.modelMapper.map(order, OrderDto.class);
        orderDto.setCustomerDTO(this.modelMapper.map(order.getCustomer(), CustomerDTO.class));
        List<OrderDetailDto> orderDetailDtos = this.orderDetailRepository.getOrderDetailByOrderId(order.getId())
                .stream()
                .map(orderDetail -> {
//                            List<ImeiDto> imeiDtoList = orderDetail.getImeis().stream().map(imei ->
//                                    this.modelMapper.map(imei, ImeiDto.class)
//                            ).collect(Collectors.toList());
//                            OrderDetailDto orderDetailDto = this.modelMapper.map(orderDetail, OrderDetailDto.class);
//                            ProductDetailCartDto productDetailCartDto = this.modelMapper.map(orderDetail.getProductDetail(), ProductDetailCartDto.class);
//                            productDetailCartDto.setPrice(orderDetail.getProductDetail().getPriceSell());
//                            orderDetailDto.setProductDetailCartDto(productDetailCartDto);
//                            orderDetailDto.setImeiDtoList(imeiDtoList);
//                            return orderDetailDto;
                            return this.orderDetailService.mapOrderDetailToOrderDetailDTO(orderDetail);
                        }
                ).collect(Collectors.toList());
        orderDto.setOrderDetailDtos(orderDetailDtos);
        return orderDto;
    }

}
