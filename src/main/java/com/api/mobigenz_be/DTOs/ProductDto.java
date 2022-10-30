package com.api.mobigenz_be.DTOs;

import com.api.mobigenz_be.entities.ProductDetail;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

    private Integer id;

    private String productName;

    private String description;

    private LocalDateTime ctime;

    private LocalDateTime mtime;

    private ManufacturerDto manufacturerDto;

    private ProductLineDto productLineDto;

    private Integer status;

    private List<ProductDetailDto> productDetailDtos;

    private List<OptionDto> optionDtos;

    private List<String> images;

    private List<SpecificationGroupDto> specificationGroupDtos;
}
