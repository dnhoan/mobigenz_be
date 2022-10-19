package com.api.mobigenz_be.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManufacturerDto {
    private Integer id;

    private String manufacturerName;

    private LocalDateTime ctime;

    private LocalDateTime mtime;

    private Integer status;
}
