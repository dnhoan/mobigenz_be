package com.api.mobigenz_be.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private LocalDateTime timeStamp;
    private int statusCode;
    private HttpStatus status;
    private String reason;
    private String message;
    private String develpperMessage;
    private Map<?, ?> data;
}
