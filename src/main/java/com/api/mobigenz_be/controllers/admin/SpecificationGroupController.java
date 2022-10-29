package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.OptionDto;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.DTOs.SpecificationGroupDto;
import com.api.mobigenz_be.constants.UrlConstant;
import com.api.mobigenz_be.services.OptionsService;
import com.api.mobigenz_be.services.SpecificationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
public class SpecificationGroupController {

    @Autowired
    private SpecificationGroupService specificationGroupService;

    @GetMapping("specificationGroups")
    public ResponseEntity<ResponseDTO> getList() {
        List<SpecificationGroupDto> specificationGroupDtos = this.specificationGroupService.getSpecificationGroup();
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("specificationGroups", specificationGroupDtos))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }
}
