package com.api.mobigenz_be.DTOs;
import java.time.LocalDateTime;

import com.api.mobigenz_be.entities.Cart;
import com.api.mobigenz_be.entities.ProductDetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
	private Integer id;
    private Double totalMoney;
    private Integer amount;
    private LocalDateTime mtime;
    private Cart cart;
    private ProductDetail productDetail;
}
