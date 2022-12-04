package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.ProductLineDto;

import java.util.List;

public interface ProductLineService {

    List<ProductLineDto> getProductLine();
    ProductLineDto saveProductLine(Integer manufacturerId, ProductLineDto productLineDto);
}
