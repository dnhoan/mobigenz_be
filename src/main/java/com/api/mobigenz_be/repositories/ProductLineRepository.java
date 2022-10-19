package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.DTOs.ProductLineDto;
import com.api.mobigenz_be.entities.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLineRepository extends JpaRepository<ProductLine, Integer> {

    @Query("select new com.api.mobigenz_be.DTOs.ProductLineDto(pl.id, pl.productLineName, pl.ctime, pl.mtime, pl.status) " +
            "from ProductLine pl join Product p on pl = p.productLine where p.id = :product_id")
    ProductLineDto getProductLineByProductId(@Param("product_id") Integer product_id);

}
