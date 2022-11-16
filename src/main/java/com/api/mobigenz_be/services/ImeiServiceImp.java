package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.ImeiDto;
import com.api.mobigenz_be.DTOs.ProductDetailDto;
import com.api.mobigenz_be.entities.Imei;
import com.api.mobigenz_be.entities.ProductDetail;
import com.api.mobigenz_be.repositories.ImeiRepository;
import com.api.mobigenz_be.repositories.ProductDetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public boolean removeImei(Integer id) {
        try {
            this.imeiRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
