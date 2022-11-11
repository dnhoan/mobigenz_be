package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.CartItemDTO;

public interface CartItemService {
	CartItemDTO createCartItem(CartItemDTO cartItemDTO);
	CartItemDTO updateCartItem(CartItemDTO cartItemDTO);
	void deleteCartItem(Integer id);
}
