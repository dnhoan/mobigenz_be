package com.api.mobigenz_be.services;

import com.api.mobigenz_be.entities.Specification;
import com.api.mobigenz_be.repositories.SpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SpecificationServiceImp implements SpecificationService{

    @Autowired
    private SpecificationRepository specificationRepository;

    @Override
    public List<Specification> getSpecificationsBySpecificationGroupIdAndProductId(Integer specification_group_id, Integer product_id) {
        return this.specificationRepository.getSpecificationsBySpecificationGroupIdAndProductId(specification_group_id, product_id);
    }
    @Override
    public List<Specification> getSpecificationsBySpecificationGroupId(Integer specification_group_id) {
        return this.specificationRepository.getSpecificationsBySpecificationGroupId(specification_group_id);
    }
}

