package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> getList();

    List<EmployeeDto> findEmployee(String search);

    EmployeeDto create(EmployeeDto employeeDto);

    EmployeeDto update(EmployeeDto employeeDto);

    EmployeeDto delete(EmployeeDto employeeDto);
}
