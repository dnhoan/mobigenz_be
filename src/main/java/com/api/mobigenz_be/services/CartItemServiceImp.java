package com.api.mobigenz_be.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.api.mobigenz_be.DTOs.CartItemDTO;
import com.api.mobigenz_be.DTOs.ProductDetailCartDto;
import com.api.mobigenz_be.entities.CartItem;
import com.api.mobigenz_be.repositories.CartItemRepository;
import com.api.mobigenz_be.repositories.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImp implements CartItemService {
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	private CartItemDTO cartItemMapToCartItemDto(CartItem cartItem) {
		ProductDetailCartDto productDetailCartDto = this.cartRepository.getProductDetailByCartItemId(cartItem.getId());
		return CartItemDTO
				.builder()
				.id(cartItem.getId())
				.amount(cartItem.getAmount())
				.productDetailCartDto(productDetailCartDto)
				.build();
	}
	
	private CartItem cartItemDtoMapToCartItem(CartItemDTO cartItemDTO) {
		return CartItem
				.builder()
				.id(cartItemDTO.getId())
				.amount(cartItemDTO.getAmount())
				.productDetail(this.cartItemRepository.getProductDetailById(cartItemDTO.getProductDetailCartDto().getId()))
				.build();
	}

	@Override
	public CartItemDTO createCartItem(CartItemDTO cartItemDTO) {
		CartItem cartItem = this.cartItemRepository.saveAndFlush(cartItemDtoMapToCartItem(cartItemDTO));
		return this.cartItemMapToCartItemDto(cartItem);
	}

	@Override
	public CartItemDTO updateCartItem(CartItemDTO cartItemDTO) {
		CartItem cartItem = this.cartItemRepository.save(cartItemDtoMapToCartItem(cartItemDTO));
		return this.cartItemMapToCartItemDto(cartItem);
	}

	@Override
	public void deleteCartItem(Integer id) {
		this.cartItemRepository.deleteById(id);
	}
}
