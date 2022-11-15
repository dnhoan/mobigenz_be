package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.CartItemDTO;

import java.util.List;

public interface CartItemService {
	CartItemDTO addCartItem(CartItemDTO cartItemDTO, Integer customerId);
	CartItemDTO updateCartItem(CartItemDTO cartItemDTO);
	void deleteCartItem(Integer id);

	List<CartItemDTO> getAll();
}
