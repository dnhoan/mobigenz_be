package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.DTOs.SpecificationGroupDto;
import com.api.mobigenz_be.constants.UrlConstant;
import com.api.mobigenz_be.services.SpecificationGroupService;
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

    @PostMapping("specificationGroup")
    public ResponseEntity<ResponseDTO> insertSpecificationGroup(@RequestBody String specificationGroupName){
        SpecificationGroupDto specificationGroupDto = this.specificationGroupService.insertSpecificationGroup(specificationGroupName);
        return ResponseEntity.ok(
                ResponseDTO
                        .builder()
                        .data(Map.of("specificationGroup", specificationGroupDto))
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .timeStamp(LocalDateTime.now())
                        .build()

        );
    }
}
