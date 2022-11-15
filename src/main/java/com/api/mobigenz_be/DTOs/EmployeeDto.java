package com.api.mobigenz_be.DTOs;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.api.mobigenz_be.entities.Employee} entity
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto implements Serializable {
    private  Integer id;
    private  String employeeName;
    private  String employeeCode;
    private  AccountDTO accountDtos;
    private  String phoneNumber;
    private  String address;
    private  LocalDate birthday;
    private  Integer gender;
    private  String image;
    private  String email;
    private  String cmnd;
    private  Float salary;
    private  LocalDate timeOnboard;
    private  LocalDate dayOff;
    private  String note;
    private  Integer status;
}