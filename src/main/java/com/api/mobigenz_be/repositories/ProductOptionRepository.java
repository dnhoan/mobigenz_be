package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.entities.ProductsOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductsOption, Integer> {
}
