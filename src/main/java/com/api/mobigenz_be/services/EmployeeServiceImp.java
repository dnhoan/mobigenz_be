package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.AccountDTO;
import com.api.mobigenz_be.DTOs.EmployeeDto;
import com.api.mobigenz_be.entities.Account;
import com.api.mobigenz_be.entities.Employee;
import com.api.mobigenz_be.repositories.AccountRepository;
import com.api.mobigenz_be.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImp implements EmployeeService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public List<EmployeeDto> getList() {
        List<Employee> employees= this.employeeRepository.findAll();
        return employees.stream().map(this::employeeMapToEmployeeDto).collect(Collectors.toList());
    }

    @Transactional
    public List<EmployeeDto> findEmployee(String search) {
        List<Employee> employees= this.employeeRepository.findByAll(search);
        return employees.stream().map(this::employeeMapToEmployeeDto).collect(Collectors.toList());
    }

    @Transactional
    public EmployeeDto create(EmployeeDto employeeDto) {
        Employee employee = this.employeeRepository.save(employeeDtoMapToEmployee(employeeDto));
        return this.employeeMapToEmployeeDto(employee);
    }

    @Transactional
    public EmployeeDto update(EmployeeDto employeeDto) {
        Employee employee = this.employeeRepository.save(employeeDtoMapToEmployee2(employeeDto));
        return this.employeeMapToEmployeeDto(employee);
    }

    @Transactional
    public EmployeeDto delete(EmployeeDto employeeDto) {
        employeeDto.setStatus(0);
        Employee employee = this.employeeRepository.save(employeeDtoMapToEmployee2(employeeDto));
        return this.employeeMapToEmployeeDto(employee);
    }

    private AccountDTO accountMapToAccountDto(Account account){
        return AccountDTO.builder()
                .id(account.getId())
                .phoneNumber(account.getPhoneNumber())
                .password(account.getPassword())
                .email(account.getEmail())
                .status(account.getStatus())
                .ctime(account.getCtime())
//                .roleid(account.getRoleid())
                .build();
    }

    private EmployeeDto employeeMapToEmployeeDto(Employee employee){
        Account account = this.accountRepository.getAccountByEmployeeId(employee.getId());

        return EmployeeDto.builder()
                .id(employee.getId())
                .employeeName(employee.getEmployeeName())
                .employeeCode(employee.getEmployeeCode())
                .birthday(employee.getBirthday())
                .image(employee.getImage())
                .email(employee.getEmail())
                .address(employee.getAddress())
                .phoneNumber(employee.getPhoneNumber())
                .salary(employee.getSalary())
                .gender(employee.getGender())
                .cmnd(employee.getCmnd())
                .note(employee.getNote())
                .accountDtos(this.accountMapToAccountDto(account))
                .timeOnboard(employee.getTimeOnboard())
                .dayOff(employee.getDayOff())
                .status(employee.getStatus())
                .build();
    }

    private Employee employeeDtoMapToEmployee(EmployeeDto employeeDto){
        Account account = modelMapper.map(employeeDto.getAccountDtos(), Account.class);

        Employee employee = Employee
                .builder()
                .employeeName(employeeDto.getEmployeeName())
                .employeeCode(employeeDto.getEmployeeCode())
                .birthday(employeeDto.getBirthday())
                .email(employeeDto.getEmail())
                .image(employeeDto.getImage())
                .address(employeeDto.getAddress())
                .cmnd(employeeDto.getCmnd())
                .phoneNumber(employeeDto.getPhoneNumber())
                .gender(employeeDto.getGender())
                .salary(employeeDto.getSalary())
                .timeOnboard(LocalDate.now())
                .dayOff(employeeDto.getDayOff())
                .note(employeeDto.getNote())
                .account(account)
                .build();
        return employee;
    }


    private Employee employeeDtoMapToEmployee2(EmployeeDto employeeDto){
        Account account = modelMapper.map(employeeDto.getAccountDtos(), Account.class);

        Employee employee = Employee
                .builder()
                .employeeName(employeeDto.getEmployeeName())
                .employeeCode(employeeDto.getEmployeeCode())
                .birthday(employeeDto.getBirthday())
                .email(employeeDto.getEmail())
                .image(employeeDto.getImage())
                .address(employeeDto.getAddress())
                .cmnd(employeeDto.getCmnd())
                .phoneNumber(employeeDto.getPhoneNumber())
                .gender(employeeDto.getGender())
                .salary(employeeDto.getSalary())
                .dayOff(employeeDto.getDayOff())
                .note(employeeDto.getNote())
                .account(account)
                .build();
        return employee;
    }


}
