package com.api.mobigenz_be.DTOs;


import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Integer id;
    private String customerName;
    private String  phoneNumber;
    private LocalDate birthday;
    private String image;
    private String email;
    private Integer gender;
    private Integer customerType;
    private AccountDTO accountDTO;
    private String citizenIdentifyCart;
    private LocalDate ctime;
    private LocalDate mtime;
    private Integer status;
}
