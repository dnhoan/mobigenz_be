package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.OptionDto;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.constants.UrlConstant;
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
public class OptionController {

    @Autowired
    private OptionsService optionsService;

    @GetMapping("options")
    public ResponseEntity<ResponseDTO> getList() {
        List<OptionDto> optionList = this.optionsService.getList();
        return ResponseEntity.ok(
            ResponseDTO.builder()
                    .data(Map.of("options", optionList))
                    .status(OK)
                    .statusCode(OK.value())
                    .timeStamp(LocalDateTime.now())
                    .build()
        );
    }

    @PostMapping("options")
    public ResponseEntity<ResponseDTO> createOption(@RequestBody OptionDto optionDto){
        OptionDto optionDto1 = this.optionsService.insertOption(optionDto);
        return ResponseEntity.ok(
                ResponseDTO
                        .builder()
                        .data(Map.of("product", optionDto1))
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .timeStamp(LocalDateTime.now())
                        .build()

        );
    }
}
