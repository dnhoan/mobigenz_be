package com.api.mobigenz_be.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.mobigenz_be.DTOs.ProductDetailCartDto;
import com.api.mobigenz_be.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	@Query("select c from Cart c where c.customer.id = :customer_id")
	List<Cart> getCartByCustomerId(@Param("customer_id") Integer customer_id);
	
	@Query("select new com.api.mobigenz_be.DTOs.ProductDetailCartDto(pd.id, pd.price, pd.sku, pd.stock, pd.image, pd.note, pd.status, pd.productName)"
			+ "from ProductDetail pd join CartItem c on pd = c.productDetail where c.id = :cart_item_id")
	ProductDetailCartDto getProductDetailByCartItemId(@Param("cart_item_id") Integer cart_item_id);
}
