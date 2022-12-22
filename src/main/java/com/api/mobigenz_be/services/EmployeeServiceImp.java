package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.EmployeeDto;
import com.api.mobigenz_be.DTOs.PageDTO;
import com.api.mobigenz_be.entities.Account;
import com.api.mobigenz_be.entities.Employee;
import com.api.mobigenz_be.repositories.AccountRepository;
import com.api.mobigenz_be.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImp implements EmployeeService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PageDTO<EmployeeDto> getAll(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Employee> page = this.employeeRepository.getAll(pageable);
        List<EmployeeDto> employeeDTOList = page.stream().map(u -> this.modelMapper.map(u, EmployeeDto.class)).collect(Collectors.toList());
        return new PageDTO<EmployeeDto>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                employeeDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

    public Page<Employee> findByKey(Pageable pageable, String valueSearch) {
        return this.employeeRepository.findByKey(pageable,valueSearch);
    }


    public EmployeeDto create(Employee employee) throws SQLException {
        employee.setTimeOnboard(LocalDate.now());
        employee.setStatus(1);
        this.employeeRepository.saveAndFlush(employee);
        return this.modelMapper.map(employee, EmployeeDto.class);
    }

    public EmployeeDto update(Employee employee) {
        employee = this.employeeRepository.save(employee);
        return this.modelMapper.map(employee, EmployeeDto.class);
    }

    public Employee findByEmployeeName(String employeeName){
        return this.employeeRepository.findByEmployeeName(employeeName);

    }

    public Employee findByEmail(String email){
        return this.employeeRepository.findByEmail(email);

    }

    public EmployeeDto delete(Employee employee) {
        employee.setDayOff(LocalDate.now());
        employee.setStatus(0);
        this.employeeRepository.save(employee);
        return this.modelMapper.map(employee, EmployeeDto.class);
    }

    public Employee findEmployeeById(Integer id){
        return this.employeeRepository.findEmployeeById(id);
    }


    public List<EmployeeDto> getAllEmployee() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Employee> page = this.employeeRepository.getAll(pageable);


        List<EmployeeDto> employeeDTOList = page.stream().map(u ->
                this.modelMapper.map(u, EmployeeDto.class)).collect(Collectors.toList());
        return employeeDTOList;
    }

    public List<EmployeeDto> searchByAll(String search) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Employee> page = this.employeeRepository.findByAll(search, pageable);


        List<EmployeeDto> employeeDtoList = page.stream().map(u -> this.modelMapper.map(u, EmployeeDto.class)).collect(Collectors.toList());
        return employeeDtoList;
    }

    public EmployeeDto getById(Integer id) throws Exception {
        Optional<Employee> employee = this.employeeRepository.findById(id);
        if (employee.isPresent()) {
            return this.modelMapper.map(employee.get(), EmployeeDto.class);
        }
        throw new Exception("Not found category by id");
    }


    public Employee findByAccountId(Integer accountId){
        return this.employeeRepository.findByAccountId(accountId);
    }

    public Page<Employee> findByStatus(Pageable pageable, Integer status) {
        return this.employeeRepository.findEmployeeByStatus(pageable, status);
    }
}
