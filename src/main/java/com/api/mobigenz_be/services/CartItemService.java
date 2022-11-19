package com.api.mobigenz_be.services;
import com.api.mobigenz_be.DTOs.CartDTO;
import com.api.mobigenz_be.DTOs.CartItemDTO;
import com.api.mobigenz_be.entities.Cart;

import java.util.List;

public interface CartItemService {
	CartItemDTO addCartItem(CartItemDTO cartItemDTO, Integer customerId);
	CartItemDTO updateCartItem(CartItemDTO cartItemDTO, Integer cart_id);
	void deleteCartItem(Integer id);

	List<CartItemDTO> getAll();
}
