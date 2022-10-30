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

    @Query("select sp from Specification sp where sp.specificationGroup.id = :specification_id")
    List<Specification> getSpecificationsBySpecificationGroupId(@Param("specification_id") Integer specification_id);
}
