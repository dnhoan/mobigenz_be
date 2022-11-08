package com.api.mobigenz_be.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
	private Integer id;
    private Integer amount;
    private CartDTO cartDto;
    private ProductDetailCartDto productDetailCartDto;
}
