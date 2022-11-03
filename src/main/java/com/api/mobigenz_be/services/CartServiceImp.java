package com.api.mobigenz_be.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.mobigenz_be.entities.Cart;
import com.api.mobigenz_be.repositories.CartRepository;

import java.util.List;

@Service
public class CartServiceImp implements CartService {
	@Autowired
	private CartRepository cartRepository;
	
	@Override
	public List<Cart> getAll() {
		return this.cartRepository.findAll();
	}

	@Override
	public List<Cart> getCartByCustomerId(Integer cid) {
		return this.cartRepository.getCartByCustomerId(cid);
	}

	@Override
	public Cart create(Cart cart) {
		return this.cartRepository.saveAndFlush(cart);
	}

	@Override
	public Cart update(Cart cart) {
		return this.cartRepository.save(cart);
	}

	@Override
	public void delete(Integer id) {
		this.cartRepository.deleteById(id);
	}
	
}
