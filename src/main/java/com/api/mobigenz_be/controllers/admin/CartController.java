package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.CartDTO;
import com.api.mobigenz_be.DTOs.CustomerDTO;
import com.api.mobigenz_be.DTOs.PageDTO;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.entities.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.mobigenz_be.services.CartService;
import static org.springframework.http.HttpStatus.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CartController {
	@Autowired
	private CartService cartService;
	
	@GetMapping("carts")
	public ResponseEntity<ResponseDTO> getCarts() {
		List<CartDTO> cartDtos = this.cartService.getCartByCustomerId(1);
		return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("cartDtos", cartDtos))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
	}
	
	@GetMapping("cart/{cus_id}")
	public ResponseEntity<ResponseDTO> getCartByCustomer(@PathVariable("cus_id") Integer cus_id) {
		List<CartDTO> cartDtos = this.cartService.getCartByCustomerId(cus_id);
		return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("cartDtos", cartDtos))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
	}
}
