package com.api.mobigenz_be.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManufacturerDto {

    public ManufacturerDto(Integer id, String manufacturerName, LocalDateTime ctime, LocalDateTime mtime, Integer status) {
        this.id = id;
        this.manufacturerName = manufacturerName;
        this.ctime = ctime;
        this.mtime = mtime;
        this.status = status;
    }

    private Integer id;

    private String manufacturerName;

    private List<ProductLineDto> productLineDtos;

    private LocalDateTime ctime;

    private LocalDateTime mtime;

    private Integer status;
}
