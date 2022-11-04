package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.DTOs.SpecificationDto;
import com.api.mobigenz_be.constants.UrlConstant;
import com.api.mobigenz_be.services.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("api/admin")
@CrossOrigin(UrlConstant.baseUrlFE)
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;


    @GetMapping("specifications")
    public ResponseEntity<ResponseDTO> getList() {
        List<SpecificationDto> specificationDto = this.specificationService.getList();
        return ResponseEntity.ok(
                ResponseDTO
                        .builder()
                        .data(Map.of("specifications", specificationDto))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );

    }

    @PostMapping("specifications")
    public ResponseEntity<ResponseDTO> insertSpecification(@RequestBody SpecificationDto specificationDto) {
        SpecificationDto specificationDto1 = this.specificationService.insertSpecification(specificationDto);
        return ResponseEntity.ok(
                ResponseDTO
                        .builder()
                        .data(Map.of("specifications", specificationDto1))
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );

    }
}
