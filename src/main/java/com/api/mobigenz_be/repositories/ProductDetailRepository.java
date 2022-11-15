package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.entities.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository  extends JpaRepository<ProductDetail, Integer> {
}
