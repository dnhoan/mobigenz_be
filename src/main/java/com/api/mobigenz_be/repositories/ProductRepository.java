package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.DTOs.ProductDto;
import com.api.mobigenz_be.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p where  lower(p.productName) like  lower(concat('%', :searchTerm,'%')) ")
    List<Product> searchProducts(@Param("searchTerm") String searchTerm);

    Product getProductById(Integer id);

}
