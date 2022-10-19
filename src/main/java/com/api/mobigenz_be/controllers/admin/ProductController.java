package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
public class ProductController {

    @GetMapping("products")
    public ResponseEntity<ResponseDTO> getList() {
        return ResponseEntity.ok(
                ResponseDTO.builder().build()
        );
    }
}
