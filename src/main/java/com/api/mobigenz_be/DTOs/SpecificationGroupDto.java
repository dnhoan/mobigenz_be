package com.api.mobigenz_be.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpecificationGroupDto {
    public SpecificationGroupDto(Integer id, String specificationGroupName) {
        this.id = id;
        this.specificationGroupName = specificationGroupName;
    }

    private Integer productSpecificationGroupId;

    private Integer id;

    private String specificationGroupName;

    private List<SpecificationDto> specificationDtos;

    private LocalDateTime ctime;

    private LocalDateTime mtime;

    private Integer status;

}
