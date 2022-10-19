package com.api.mobigenz_be.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductLineDto {
    private Integer id;

    private String productLineName;

    private LocalDateTime ctime;

    private LocalDateTime mtime;

    private Integer status;
}
