package com.api.mobigenz_be.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailCartDto {
    private Integer id;

    private Double price;

    private String sku;

    private String image;
    
    private Integer stock;
    
    private String productName;
}
