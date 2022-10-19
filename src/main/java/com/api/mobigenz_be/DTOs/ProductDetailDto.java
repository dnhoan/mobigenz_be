package com.api.mobigenz_be.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailDto {
    private Integer id;

    private Double price;

    private String sku;

    private Integer stock;

    private String image;

    private String note;

    private Integer status;

    private List<ProductVariantCombinationDto> productVariantCombinationDtos;
}
