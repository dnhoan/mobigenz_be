package com.api.mobigenz_be.DTOs;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {

    private Integer id;

    private String customerName;

    private String  phoneNumber;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;

    private String image;

    private String email;

    private Integer gender;

    private Integer customerType;
    private AccountDTO accountDTO;
    private String citizenIdentifyCart;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate ctime;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate mtime;

    private Integer status;

}
