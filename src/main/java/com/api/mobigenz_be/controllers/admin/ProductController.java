package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.PageDTO;
import com.api.mobigenz_be.DTOs.ProductDto;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.constants.Constant;
import com.api.mobigenz_be.constants.UrlConstant;
import com.api.mobigenz_be.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin(UrlConstant.baseUrlFE)
@RequestMapping(Constant.Api.Path.ADMIN)
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("products")
    public ResponseEntity<ResponseDTO> getList(
            @RequestParam(value = "searchTerm", defaultValue = "", required = false) String searchTerm) {
        System.out.println("get products");
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("products", this.productService.getProducts(searchTerm)))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @PostMapping("product")
    public ResponseEntity<ResponseDTO> createProduct(@RequestBody ProductDto productDto) {
        ProductDto productDto1 = this.productService.saveProduct(productDto);
        return ResponseEntity.ok(
                ResponseDTO
                        .builder()
                        .data(Map.of("product", productDto1))
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("product")
    public ResponseEntity<ResponseDTO> updateProduct(@RequestBody ProductDto productDto) {
        ProductDto productDto1 = this.productService.saveProduct(productDto);
        return ResponseEntity.ok(
                ResponseDTO
                        .builder()
                        .data(Map.of("product", productDto1))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("product/{product_id}")
    public ResponseEntity<ResponseDTO> getProductById(@PathVariable("product_id") Integer product_id) {
        ProductDto productDto = this.productService.getProductDtoById(product_id);
        return ResponseEntity.ok(
                ResponseDTO
                        .builder()
                        .data(Map.of("product", productDto))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }
}
