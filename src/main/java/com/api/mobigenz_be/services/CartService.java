package com.api.mobigenz_be.services;

import java.util.List;

import com.api.mobigenz_be.entities.Cart;

public interface CartService {
	List<Cart> getAll();
	List<Cart> getCartByCustomerId(Integer cid);
	Cart create(Cart cart);
	Cart update(Cart cart);
	void delete(Integer id);
}
