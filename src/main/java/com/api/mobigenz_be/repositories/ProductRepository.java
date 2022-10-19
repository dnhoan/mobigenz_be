package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.DTOs.ProductDto;
import com.api.mobigenz_be.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

//    @Query("SELECT " +
//            "new com.api.mobigenz_be.DTOs.ProductDto(" +
//                "p.id, " +
//                "p.productName, " +
//                "p.description, " +
//                "p.ctime, " +
//                "p.mtime, " +
//                "p.status) " +
//            "FROM Product p ")
//    List<ProductDto> getProducts();

}
