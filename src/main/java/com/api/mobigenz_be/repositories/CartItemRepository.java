package com.api.mobigenz_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.mobigenz_be.entities.ProductDetail;
import com.api.mobigenz_be.entities.CartItem;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
	@Query("select pd from ProductDetail pd where pd.id = :pd_id")
	ProductDetail getProductDetailById(@Param("pd_id") Integer pd_id);

	Optional<CartItem> getCartItemByProductDetailIdAndCartId(Integer productDetailId, Integer cartId);
}
