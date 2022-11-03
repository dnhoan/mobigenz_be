package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.constants.UrlConstant;
import com.api.mobigenz_be.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(UrlConstant.baseUrlFE)
@RequestMapping("api/test/")
public class TestController {

    @Autowired
    private ProductService productService;

    @GetMapping("products")
    public ResponseEntity<ResponseDTO> getList() {
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("products", this.productService.getProducts()))
                        .build()
        );
    }
}
