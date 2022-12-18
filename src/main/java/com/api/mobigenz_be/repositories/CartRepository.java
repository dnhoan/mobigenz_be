package com.api.mobigenz_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.mobigenz_be.DTOs.CartDTO;
import com.api.mobigenz_be.DTOs.ProductDetailCartDto;
import com.api.mobigenz_be.entities.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	@Query("select c from Cart c where c.customer.id = :customer_id")
	Optional<Cart> getCartByCustomerId(@Param("customer_id") Integer customer_id);
	
	@Query("select new com.api.mobigenz_be.DTOs.ProductDetailCartDto(pd.id, pd.priceSell, pd.sku, pd.image, pd.stock, pd.productName)"
			+ "from ProductDetail pd join CartItem c on pd.id = c.productDetailId where c.id = :cart_item_id")
	ProductDetailCartDto getProductDetailByCartItemId(@Param("cart_item_id") Integer cart_item_id);

	boolean existsCartByCustomerId(Integer customerId);

	void deleteCartByCustomerId(Integer customerId);

}
