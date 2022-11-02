package com.api.mobigenz_be.services;

import com.api.mobigenz_be.entities.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SpecificationService {

    List<Specification> getSpecificationsBySpecificationGroupIdAndProductId(Integer specification_group_id, Integer product_id);
    List<Specification> getSpecificationsBySpecificationGroupId(Integer specification_group_id);
}
