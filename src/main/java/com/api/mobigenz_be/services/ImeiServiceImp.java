package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.ImeiDto;
import com.api.mobigenz_be.DTOs.ImeiUpload;
import com.api.mobigenz_be.entities.Imei;
import com.api.mobigenz_be.entities.OrderDetail;
import com.api.mobigenz_be.entities.ProductDetail;
import com.api.mobigenz_be.repositories.ImeiRepository;
import com.api.mobigenz_be.repositories.ProductDetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class ImeiServiceImp implements ImeiService {

    @Autowired
    private ImeiRepository imeiRepository;
    @Autowired
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public List<ImeiDto> getImeisByProductDetailId(Integer productDetailId) {
        List<Imei> imeiList = this.imeiRepository.getImeiByProductDetailId(productDetailId);
        return imeiList.stream().map(imei -> this.modelMapper.map(imei, ImeiDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ImeiDto> getImeisInStockByProductDetailId(Integer productDetailId) {
        List<Imei> imeiList = this.imeiRepository.getImeisInStockByProductDetailId(productDetailId);
        return imeiList.stream().map(imei -> this.modelMapper.map(imei, ImeiDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ImeiDto> addImeisToOrderDetail(List<ImeiDto> imeiDtoList, Integer order_detail_id, Integer product_detail_id) {
        List<Imei> imeis = imeiDtoList.stream().map(imeiDto -> {
            Imei imei = this.modelMapper.map(imeiDto, Imei.class);
            imei.setOrderDetail(OrderDetail.builder().id(order_detail_id).build());
            imei.setProductDetail(ProductDetail.builder().id(product_detail_id).build());
            imei.setStatus(0);
            return imei;
        }).collect(Collectors.toList());
        imeis = this.imeiRepository.saveAllAndFlush(imeis);
        return imeis.stream().map(imei ->
                this.modelMapper.map(imei, ImeiDto.class)
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ImeiDto save(ImeiDto imeiDto) {
        Imei imei = this.modelMapper.map(imeiDto, Imei.class);
        imei.setId(imeiDto.getId());
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId(imeiDto.getProductDetailDto().getId());
        imei.setProductDetail(productDetail);
        this.imeiRepository.saveAndFlush(imei);
        return this.modelMapper.map(imei, ImeiDto.class);
    }

    @Override
    @Transactional
    public List<ImeiDto> batchSave(Integer productDetailId, List<ImeiUpload> imeiUploads) {
        List<Imei> imeis = imeiUploads.stream().map(imeiUpload ->
                Imei
                        .builder()
                        .imei(imeiUpload.getImei())
                        .status(1)
                        .productDetail(ProductDetail.builder().id(productDetailId).build())
                        .build()
        ).collect(Collectors.toList());
        imeis = this.imeiRepository.saveAllAndFlush(imeis);
        return imeis.stream().map(imei -> this.modelMapper.map(imei, ImeiDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ImeiUpload> validateBatchImei(Integer productDetailId, List<ImeiUpload> imeiUploads) {
        AtomicBoolean hasError = new AtomicBoolean(false);
            imeiUploads = imeiUploads.stream().peek(imeiUpload -> {
                if(this.imeiRepository.existsImeiByImei(imeiUpload.getImei())) {
                    imeiUpload.setError("Imei đã tồn tại");
                    hasError.set(true);
                }
            }).collect(Collectors.toList());

        return hasError.get() ? imeiUploads : new ArrayList<>();
    }

    @Override
    @Transactional
    public boolean removeImei(Integer id) {
        try {
            this.imeiRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Transactional
    public boolean exchangeImeiTheSameOrderDetail(Integer newImei, Integer oldImei, Integer orderDetailId) {
        System.out.println(newImei + " " + oldImei + " " + orderDetailId);
        this.imeiRepository.deleteOrderDetailToImei(oldImei);
        this.imeiRepository.exchangeImeiTheSameOrderDetail(orderDetailId, newImei);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteOrderDetailToImei(Integer id) {
        this.imeiRepository.deleteOrderDetailToImei(id);
        return true;
    }
}
