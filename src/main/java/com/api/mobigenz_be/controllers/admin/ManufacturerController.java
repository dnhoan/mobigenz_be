package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.ManufacturerDto;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.constants.UrlConstant;
import com.api.mobigenz_be.entities.Manufacturer;
import com.api.mobigenz_be.services.ManufacturersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/admin")
@CrossOrigin(UrlConstant.baseUrlFE)
public class ManufacturerController {
    @Autowired
    private ManufacturersService manufacturersService;

    @GetMapping("manufacturers")
    public ResponseEntity<ResponseDTO> getList() {
        List<ManufacturerDto> manufacturers = this.manufacturersService.getList();
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("manufacturers", manufacturers))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }
}
