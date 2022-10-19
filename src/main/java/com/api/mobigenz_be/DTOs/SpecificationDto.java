package com.api.mobigenz_be.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpecificationDto {

    private Integer id;

    private String specificationName;

    private LocalDateTime ctime;

    private LocalDateTime mtime;

    private Integer status;

    private List<ProductSpecificationDto> productSpecificationDtos;
}
