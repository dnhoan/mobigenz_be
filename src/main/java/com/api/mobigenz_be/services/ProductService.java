package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.ProductDto;
import com.api.mobigenz_be.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {

    List<ProductDto> getProducts(String searchTerm);
    List<ProductDto> searchProducts(String searchTerm, boolean sortPriceIncrease, Float min_price,  Float max_price,Integer manufacturer);
    ProductDto saveProduct(ProductDto productDto);
    ProductDto getProductDtoById(Integer id);

}
