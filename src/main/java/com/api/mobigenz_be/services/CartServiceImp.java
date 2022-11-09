package com.api.mobigenz_be.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.mobigenz_be.DTOs.CartDTO;
import com.api.mobigenz_be.DTOs.CartItemDTO;
import com.api.mobigenz_be.DTOs.ProductDetailCartDto;
import com.api.mobigenz_be.entities.Cart;
import com.api.mobigenz_be.entities.CartItem;
import com.api.mobigenz_be.repositories.CartRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImp implements CartService {
	@Autowired
	private CartRepository cartRepository;
	
	@Override
	public List<CartDTO> getAll() {
		return this.cartRepository.findAll()
				.stream()
                .map(this::cartMapToCartDto)
                .collect(Collectors.toList());
	}
	
	private CartDTO cartMapToCartDto(Cart cart) {
		List<CartItemDTO> cartItemDTOs = cart.getCartItems()
				.stream()
				.map(this::cartItemMapToCartItemDto)
				.collect(Collectors.toList());
		return CartDTO
				.builder()
				.id(cart.getId())
				.totalMoney(cart.getTotalMoney())
				.itemsAmount(cart.getItemsAmount())
				.mtime(cart.getMtime())
				.cartItemDtos(cartItemDTOs)
				.build();
	}
	
	private CartItemDTO cartItemMapToCartItemDto(CartItem cartItem) {
		// get product detail by cart item id
		ProductDetailCartDto productDetailCartDto = cartRepository.getProductDetailByCartItemId(cartItem.getId());
		return CartItemDTO
				.builder()
				.id(cartItem.getId())
				.amount(cartItem.getAmount())
				.productDetailCartDto(productDetailCartDto)
				.build();
	}

	@Override
	public List<CartDTO> getCartByCustomerId(Integer cid) {
		return this.cartRepository.getCartByCustomerId(cid)
				.stream()
				.map(this::cartMapToCartDto)
				.collect(Collectors.toList());
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
