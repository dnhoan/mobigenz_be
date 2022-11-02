package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.entities.Specification;
import com.api.mobigenz_be.entities.SpecificationGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecificationRepository extends JpaRepository<Specification, Integer> {

    @Query("select spe from Specification spe join ProductsSpecification productSpe on productSpe.specification = spe " +
            "join SpecificationGroup speg on spe.specificationGroup = speg " +
            "join ProductsSpecificationGroup proSpeGr on productSpe.productSpecificationGroup = proSpeGr " +
            "where   spe.specificationGroup.id = :specification_id and proSpeGr.product.id = :product_id")
    List<Specification> getSpecificationsBySpecificationGroupIdAndProductId(@Param("specification_id") Integer specification_id,
                                                                            @Param("product_id") Integer product_id);
    
    @Query("select spe from Specification spe where  spe.specificationGroup.id = :specification_id ")
    List<Specification> getSpecificationsBySpecificationGroupId(@Param("specification_id") Integer specification_id);
}
