package com.api.mobigenz_be.DTOs;

import com.api.mobigenz_be.entities.Option;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionValueDto {
    private Integer id;

    private String optionValueName;

    private Integer productVariantsId;

    private String optionName;

    private OptionDto optionId;

    private String note;

    private LocalDateTime ctime;

    private LocalDateTime mtime;

    private Integer status;
}
