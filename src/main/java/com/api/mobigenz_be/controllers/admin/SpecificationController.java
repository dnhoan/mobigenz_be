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

    @PostMapping("specification/{specification_group_id}")
    public ResponseEntity<ResponseDTO> insertSpecification(
            @PathVariable("specification_group_id") Integer specification_group_id,
            @RequestBody String specification_name) {
        SpecificationDto specificationDto = this.specificationService.insertSpecification(specification_group_id, specification_name);
        return ResponseEntity.ok(
                ResponseDTO
                        .builder()
                        .data(Map.of("specification", specificationDto))
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );

    }
}
