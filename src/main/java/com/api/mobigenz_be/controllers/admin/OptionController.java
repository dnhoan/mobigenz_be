package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.entities.Option;
import com.api.mobigenz_be.services.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/admin")
public class OptionController {

    @Autowired
    private OptionsService optionsService;

    @GetMapping("options")
    public ResponseEntity<ResponseDTO> getList() {
        List<Option> optionList = this.optionsService.getList();
        return ResponseEntity.ok(
            ResponseDTO.builder()
                    .data(Map.of("options", optionList))
                    .status(OK)
                    .statusCode(OK.value())
                    .timeStamp(LocalDateTime.now())
                    .build()
        );
    }
}
