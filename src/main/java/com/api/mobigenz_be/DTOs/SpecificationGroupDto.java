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
public class SpecificationGroupDto {

    private Integer id;

    private String specificationGroupName;

    private List<SpecificationDto> specificationDtos ;

    private LocalDateTime ctime;

    private LocalDateTime mtime;

    private Integer status;

}
