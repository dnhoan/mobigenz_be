package com.api.mobigenz_be.services;

import com.api.mobigenz_be.entities.Cart;
import com.api.mobigenz_be.entities.Customer;
import com.api.mobigenz_be.entities.ProductDetail;
import org.springframework.beans.factory.annotation.Autowired;

import com.api.mobigenz_be.DTOs.CartItemDTO;
import com.api.mobigenz_be.DTOs.ProductDetailCartDto;
import com.api.mobigenz_be.entities.CartItem;
import com.api.mobigenz_be.repositories.CartItemRepository;
import com.api.mobigenz_be.repositories.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
				.productDetail(ProductDetail.builder().id(cartItemDTO.getProductDetailCartDto().getId()).build())
				.build();
	}

    @Override
    @Transactional
    public CartItemDTO addCartItem(CartItemDTO cartItemDTO, Integer customerId) {
        Cart cart = new Cart();
        Optional<Cart> cartOptional = this.cartRepository.getCartByCustomerId(customerId);
		CartItem cartItem = this.cartItemDtoMapToCartItem(cartItemDTO);
		if(cartOptional.isPresent()) {
        	Optional<CartItem> cartItemOptional = this.cartItemRepository.getCartItemByProductDetailIdAndCartId(
					cartItem.getProductDetail().getId(),
					cartOptional.get().getId()
			);
			if(cartItemOptional.isPresent()) {
				cartItem = cartItemOptional.get();
				int amount = cartItem.getAmount();
				amount++;
				cartItem.setAmount(amount);
			}
		} else {
			cart = Cart.builder()
						.customer(Customer.builder().id(customerId).build())
						.itemsAmount(0)
						.totalMoney(0.0)
						.build();
			this.cartRepository.save(cart);
        	cartItem.setCart(cart);
		}
        cartItem = this.cartItemRepository.saveAndFlush(cartItem);
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

	@Override
	public List<CartItemDTO> getAll() {
		return null;
	}
}
