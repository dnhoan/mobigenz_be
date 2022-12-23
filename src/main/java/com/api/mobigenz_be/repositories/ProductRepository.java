package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.DTOs.ProductDto;
import com.api.mobigenz_be.entities.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p where  lower(p.productName) like  lower(concat('%', :searchTerm,'%')) and p.status = 1 order by p.ctime desc ")
    List<Product> searchProducts(@Param("searchTerm") String searchTerm);

    Product getProductById(Integer id);

    @Query("SELECT p FROM Product p where  " +
            "lower(p.productName) like  lower(concat('%', :searchTerm,'%')) " +
            "and (:manufacturerId = 0 or p.productLine.manufacturer.id = :manufacturerId) " +
            "and p.minPrice >= :min_price and p.maxPrice <= :max_price and p.status = 1 ")
    List<Product> searchProductsShop(@Param("searchTerm") String searchTerm,
                                     @Param("manufacturerId") Integer manufacturerId,
                                     @Param("min_price") Float min_price,
                                     @Param("max_price") Float max_price,
                                     Sort sort
    );

    @Modifying()
    @Query("update Product p set p.status = 0 where p.id = :product_id")
    void deleteProductById(@Param("product_id") Integer product_id);
}
