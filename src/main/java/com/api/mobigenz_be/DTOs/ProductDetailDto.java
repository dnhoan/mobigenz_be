package com.api.mobigenz_be.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailDto {
    private Integer id;

    private Integer productVariantCombineId;

    private Double price;

    private Double priceOrigin;

    private Double priceSell;

    private String sku;

    private Integer stock;

    private String image;

    private String note;

    private Integer status;
    
    private String productName;

    private List<ProductVariantCombinationDto> productVariantCombinationDtos;
}
