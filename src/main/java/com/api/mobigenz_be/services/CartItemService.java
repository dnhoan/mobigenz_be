package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.CartItemDTO;

public interface CartItemService {
	CartItemDTO addCartItem(CartItemDTO cartItemDTO, Integer customerId);
	CartItemDTO updateCartItem(CartItemDTO cartItemDTO);
	void deleteCartItem(Integer id);
}
