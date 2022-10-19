package com.api.mobigenz_be.DTOs;


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
public class OptionDto {
    private Integer id;

    private String optionName;

    private String note;

    private LocalDateTime ctime;

    private LocalDateTime mtime;

    private Integer status;

    private List<OptionValueDto> optionValueDtos;

}
