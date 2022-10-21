package com.api.mobigenz_be.DTOs;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Integer id;
    private String customerName;
    private String  phoneNumber;
    private LocalDate birthday;
    private String image;
    private Integer gender;
    private Integer customerType;
    private AccountDTO account;
    private String citizenIdentifyCart;
    private Instant ctime;
    private Instant mtime;
    private Integer status;
}
