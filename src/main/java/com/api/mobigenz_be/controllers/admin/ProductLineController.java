package com.api.mobigenz_be.controllers.admin;


import com.api.mobigenz_be.DTOs.ProductLineDto;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.constants.UrlConstant;
import com.api.mobigenz_be.services.ProductLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/admin")
@CrossOrigin(UrlConstant.baseUrlFE)
public class ProductLineController {


    @Autowired
    private ProductLineService productLineService;



    @GetMapping("productLine")
    public ResponseEntity<ResponseDTO> getList(){
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("productLine",this.productLineService.getProductLine()))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );

    }

    @PostMapping("productLine/{manufacturer_id}")
    public ResponseEntity<ResponseDTO> saveProductLine(
            @PathVariable("manufacturer_id") Integer manufacturer_id,
            @RequestBody ProductLineDto productLineDto){
        productLineDto = this.productLineService.saveProductLine(manufacturer_id, productLineDto);
        return ResponseEntity.ok(
                ResponseDTO
                        .builder()
                        .data(Map.of("productLine", productLineDto))
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .timeStamp(LocalDateTime.now())
                        .build()

        );
    }


}
