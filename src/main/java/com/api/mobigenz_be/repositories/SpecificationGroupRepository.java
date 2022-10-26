package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.DTOs.SpecificationGroupDto;
import com.api.mobigenz_be.entities.SpecificationGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecificationGroupRepository extends JpaRepository<SpecificationGroup, Integer> {

    @Query("select sp from SpecificationGroup sp join ProductsSpecificationGroup psg on psg.specificationGroup = sp where psg.product.id = :product_id")
    List<SpecificationGroup> getSpecificationGroupByProductId(@Param("product_id") Integer product_id);

}
