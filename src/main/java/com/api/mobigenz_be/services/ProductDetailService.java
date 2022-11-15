package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.ProductDetailDto;
import com.api.mobigenz_be.entities.ProductDetail;

public interface ProductDetailService {

    ProductDetailDto saveProductDetailDto(ProductDetailDto productDetailDto);

    ProductDetailDto productDetailMapToProductDetailDto(ProductDetail productDetail);
}
