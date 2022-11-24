package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.ImeiDto;
import com.api.mobigenz_be.DTOs.OrderDetailDto;
import com.api.mobigenz_be.DTOs.ProductDetailCartDto;
import com.api.mobigenz_be.entities.Imei;
import com.api.mobigenz_be.entities.Order;
import com.api.mobigenz_be.entities.OrderDetail;
import com.api.mobigenz_be.entities.ProductDetail;
import com.api.mobigenz_be.repositories.ImeiRepository;
import com.api.mobigenz_be.repositories.OrderDetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImp implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public boolean deleteOrderDetail(Integer id) {
        this.orderDetailRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public OrderDetailDto addOrderDetailToOrder(Integer order_id, OrderDetailDto orderDetailDto) {
        OrderDetail orderDetail = this.modelMapper.map(orderDetailDto, OrderDetail.class);
        OrderDetail finalOrderDetail = orderDetail;
        List<Imei> imeis = orderDetailDto.getImeiDtoList().stream().map(imeiDto -> {
            Imei imei = this.modelMapper.map(imeiDto, Imei.class);
            imei.setOrderDetail(finalOrderDetail);
            imei.setStatus(0);
            return imei;
        }).collect(Collectors.toList());
        orderDetail.setImeis(imeis);
        orderDetail.setOrder(Order.builder().id(order_id).build());
        orderDetail.setProductDetail(this.modelMapper.map(orderDetailDto.getProductDetailCartDto(), ProductDetail.class));
        orderDetail = this.orderDetailRepository.saveAndFlush(orderDetail);
        return this.mapOrderDetailToOrderDetailDTO(orderDetail);
    }

    public OrderDetailDto mapOrderDetailToOrderDetailDTO(OrderDetail orderDetail) {
        List<ImeiDto> imeiDtoList = orderDetail.getImeis().stream().map(imei ->
                this.modelMapper.map(imei, ImeiDto.class)
        ).collect(Collectors.toList());
        OrderDetailDto orderDetailDto = this.modelMapper.map(orderDetail, OrderDetailDto.class);
        ProductDetailCartDto productDetailCartDto = this.modelMapper.map(orderDetail.getProductDetail(), ProductDetailCartDto.class);
        productDetailCartDto.setPrice(orderDetail.getProductDetail().getPriceSell());
        orderDetailDto.setProductDetailCartDto(productDetailCartDto);
        orderDetailDto.setImeiDtoList(imeiDtoList);
        return orderDetailDto;
    }
}
