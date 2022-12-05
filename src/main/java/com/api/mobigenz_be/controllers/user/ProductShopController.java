package com.api.mobigenz_be.controllers.user;

import com.api.mobigenz_be.DTOs.ManufacturerDto;
import com.api.mobigenz_be.DTOs.ProductDto;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.constants.Constant;
import com.api.mobigenz_be.constants.UrlConstant;
import com.api.mobigenz_be.services.ManufacturersService;
import com.api.mobigenz_be.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin(UrlConstant.baseUrlFE)
@RequestMapping("api/user")
public class ProductShopController {

    @Autowired
    private ManufacturersService manufacturersService;
    @Autowired
    private ProductService productService;

    @GetMapping("searchProducts")
    public ResponseEntity<ResponseDTO> searchProducts(
            @RequestParam(value = "searchTerm", defaultValue = "", required = false) String searchTerm,
            @RequestParam(value = "sortPriceIncrease", defaultValue = "true", required = false) boolean sortPriceIncrease,
            @RequestParam(value = "min_price", defaultValue = "0", required = false) Float min_price,
            @RequestParam(value = "max_price", defaultValue = "999999999", required = false) Float max_price,
            @RequestParam(value = "manufacturer", defaultValue = "", required = false) Integer manufacturer
            ) {
        List<ProductDto> productDtos = this.productService.searchProducts(searchTerm,sortPriceIncrease , min_price, max_price,manufacturer );
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("products", productDtos))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("manufacturers")
    public ResponseEntity<ResponseDTO> getList() {
        List<ManufacturerDto> manufacturers = this.manufacturersService.getList();
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("manufacturers", manufacturers))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }
}
