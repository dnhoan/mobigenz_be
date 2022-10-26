package com.api.mobigenz_be.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpecificationDto {
    public SpecificationDto(Integer id, String specificationName) {
        this.id = id;
        this.specificationName = specificationName;
    }

    private Integer id;

    private String specificationName;

    private LocalDateTime ctime;

    private LocalDateTime mtime;

    private Integer status;

    private List<ProductSpecificationDto> productSpecificationDtos;
}
