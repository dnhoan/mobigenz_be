package com.api.mobigenz_be.services;

import java.util.List;

import com.api.mobigenz_be.DTOs.CartDTO;
import com.api.mobigenz_be.entities.Cart;


public interface CartService {
	List<CartDTO> getAll();
	CartDTO getCartByCustomerId(Integer cid);
	CartDTO create(CartDTO cartDTO);
	Cart update(Cart cart);
	void delete(Integer id);
}
