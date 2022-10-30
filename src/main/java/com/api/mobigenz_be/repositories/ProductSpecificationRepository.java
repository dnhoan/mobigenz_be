package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.entities.ProductsSpecification;
import com.api.mobigenz_be.entities.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSpecificationRepository extends JpaRepository<ProductsSpecification, Integer> {

    @Query("select ps from ProductsSpecification ps where ps.specification.id = :specification_id and  (:product_id is null or ps.productSpecificationGroup.product.id = :product_id)")
    List<ProductsSpecification> findProductsSpecificationBySpecificationIdAndProductId(@Param("specification_id") Integer specification_id,
                                                                                       @Param("product_id") Integer product_id);

}
