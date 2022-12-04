package com.api.mobigenz_be.controllers.user;

import com.api.mobigenz_be.DTOs.CartDTO;
import com.api.mobigenz_be.DTOs.CartItemDTO;
import com.api.mobigenz_be.DTOs.CustomerDTO;
import com.api.mobigenz_be.DTOs.PageDTO;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.entities.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.mobigenz_be.services.CartItemService;
import com.api.mobigenz_be.services.CartService;

import static org.springframework.http.HttpStatus.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("carts")
    public ResponseEntity<ResponseDTO> getCarts() {
        CartDTO cartDto = this.cartService.getCartByCustomerId(4);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("cartDto", cartDto))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("cart/{cus_id}")
    public ResponseEntity<ResponseDTO> getCartByCustomer(@PathVariable("cus_id") Integer cus_id) {
        CartDTO cartDto = this.cartService.getCartByCustomerId(cus_id);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("cartDto", cartDto))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @PostMapping("cartItem/{customer_id}")
    public ResponseEntity<ResponseDTO> addCartItem(
            @PathVariable("customer_id") Integer customer_id,
            @RequestBody CartItemDTO cartItemDTO) {
        cartItemDTO = this.cartItemService.addCartItem(cartItemDTO, customer_id);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("cartItemDto", cartItemDTO))
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("cartItem/{cart_id}")
    public ResponseEntity<ResponseDTO> saveCartItem(@PathVariable("cart_id") Integer cart_id, @RequestBody CartItemDTO cartItemDTO) {
        CartItemDTO cartItemInsertDto = this.cartItemService.updateCartItem(cartItemDTO, cart_id);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("cartItemInsertDto", cartItemInsertDto))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @DeleteMapping("cartItem/{citem_id}")
    public ResponseEntity<ResponseDTO> removeCartItem(@PathVariable("citem_id") Integer citem_id) {
        this.cartItemService.deleteCartItem(citem_id);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }
}
