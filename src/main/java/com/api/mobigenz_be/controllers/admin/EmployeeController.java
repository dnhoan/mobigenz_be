package com.api.mobigenz_be.controllers.admin;


import com.api.mobigenz_be.DTOs.EmployeeDto;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.constants.UrlConstant;
import com.api.mobigenz_be.entities.Employee;
import com.api.mobigenz_be.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin(UrlConstant.baseUrlFE)
@RequestMapping("api/admin")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("employee")
    public ResponseEntity<ResponseDTO> getList(){
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("employee", this.employeeService.getList()))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("searchEmployee")
    public ResponseEntity<ResponseDTO> searchAll(@RequestParam("search") String search){
        List<EmployeeDto> employeeDtoList = this.employeeService.findEmployee(search);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("employee", employeeDtoList))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @PostMapping("employee")
    public ResponseEntity<ResponseDTO> insertEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto employeeDto1 = this.employeeService.create(employeeDto);
        return ResponseEntity.ok(
                ResponseDTO
                        .builder()
                        .data(Map.of("employee", employeeDto1))
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }


    @PutMapping("employee")
    public ResponseEntity<ResponseDTO> updateEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto employeeDto1 = this.employeeService.update(employeeDto);
        return ResponseEntity.ok(
                ResponseDTO
                        .builder()
                        .data(Map.of("employee", employeeDto1))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity<ResponseDTO> deleteEmployee(@PathVariable("id") EmployeeDto employeeDto){
        this.employeeService.delete(employeeDto);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message("Delete success")
                        .status(OK)
                        .data(Map.of("employee", this.employeeService.getList()))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

}
