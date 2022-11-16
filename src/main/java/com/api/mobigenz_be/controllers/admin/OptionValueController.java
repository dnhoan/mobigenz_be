package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.OptionDto;
import com.api.mobigenz_be.DTOs.OptionValueDto;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.constants.UrlConstant;
import com.api.mobigenz_be.entities.OptionsValue;
import com.api.mobigenz_be.services.OptionValueService;
import com.api.mobigenz_be.services.OptionsService;
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
public class OptionValueController {
    @Autowired
    private OptionValueService optionValueService;

    @GetMapping("optionValue")
    public ResponseEntity<ResponseDTO> getList() {
        List<OptionValueDto> optionValueList = this.optionValueService.getList();
        System.out.println(optionValueList);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("optionValue", optionValueList))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @PostMapping("optionValue")
    public OptionValueDto create(@RequestBody OptionValueDto optionsValueDto){
        return this.optionValueService.saveOptionValue(optionsValueDto);
    }
}
