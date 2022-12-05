package com.api.mobigenz_be.DTOs;

import com.api.mobigenz_be.entities.ProductDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;
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

    private String detail;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ctime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime mtime;

    private boolean expand = false;

    private ManufacturerDto manufacturerDto;

    private ProductLineDto productLineDto;

    private Integer status;

    private Float minPrice;

    private Float maxPrice;

    private List<ProductDetailDto> productDetailDtos;

    private List<OptionDto> optionDtos;

    private List<String> images;

    private List<SpecificationGroupDto> specificationGroupDtos;
}
