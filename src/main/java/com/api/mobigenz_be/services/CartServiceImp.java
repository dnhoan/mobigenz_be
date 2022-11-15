package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.CustomerDTO;
import com.api.mobigenz_be.entities.Customer;
import com.api.mobigenz_be.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.mobigenz_be.DTOs.CartDTO;
import com.api.mobigenz_be.DTOs.CartItemDTO;
import com.api.mobigenz_be.DTOs.ProductDetailCartDto;
import com.api.mobigenz_be.entities.Cart;
import com.api.mobigenz_be.entities.CartItem;
import com.api.mobigenz_be.repositories.CartRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImp implements CartService {
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;
	
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



	private Cart CartDTOMapToCart(CartDTO cartDTO){

//		List<CartItem> cartItems = cartDTO.getCartItemDtos()
//				.stream().map(cartItemDTO ->  this.cartItemDtoMapToCartItem(cartItemDTO, cartDTO.getCartItemDtos()))
//				.collect(Collectors.toList());

		List<CartItem> cartItems = cartDTO.getCartItemDtos()
				.stream().map(this::cartItemDtoMapToCartItem)
				.collect(Collectors.toList());

		Cart cart = Cart.builder()
				.totalMoney(cartDTO.getTotalMoney())
				.itemsAmount(cartDTO.getItemsAmount())
				.cartItems(cartItems)
				.build();
		//cart.setCartItems(cartItems);
		return cart;
	}

	
	private CartItemDTO cartItemMapToCartItemDto(CartItem cartItem) {
		// get product detail by cart item id
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
	public CartDTO getCartByCustomerId(Integer cid) {
		return this.cartMapToCartDto(this.cartRepository.getCartByCustomerId(cid));
	}

	@Transactional
	public CartDTO create(CartDTO cartDTO){
		Cart cart = this.cartRepository.save(this.CartDTOMapToCart(cartDTO));
		return this.cartMapToCartDto(cart);
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
