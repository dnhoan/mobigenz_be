package com.api.mobigenz_be.DTOs;

import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomersAddressDto {
    private Integer id;
    private String ward;
    private String city;
    private String district;
    private String detailAddress;

    private Integer status;
}
