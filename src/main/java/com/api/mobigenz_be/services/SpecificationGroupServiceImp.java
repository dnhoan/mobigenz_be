package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.ProductSpecificationDto;
import com.api.mobigenz_be.DTOs.SpecificationDto;
import com.api.mobigenz_be.DTOs.SpecificationGroupDto;
import com.api.mobigenz_be.entities.ProductsSpecification;
import com.api.mobigenz_be.entities.Specification;
import com.api.mobigenz_be.entities.SpecificationGroup;
import com.api.mobigenz_be.repositories.SpecificationGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecificationGroupServiceImp implements SpecificationGroupService {

    @Autowired
    private SpecificationGroupRepository specificationGroupRepository;

    public List<SpecificationGroupDto> getSpecificationGroupByProductId(Integer product_id) {
        return this.specificationGroupRepository.getSpecificationGroupByProductId((product_id))
                .stream()
                .map(this::specificationGroupMapToSpecificationGroupDto)
                .collect(Collectors.toList());
    }

    private SpecificationGroupDto specificationGroupMapToSpecificationGroupDto(SpecificationGroup specificationGroup) {
        List<SpecificationDto> specificationDtos = specificationGroup.getSpecifications()
                .stream()
                .map(this::specificationMapToSpecificationDto)
                .collect(Collectors.toList());
        return SpecificationGroupDto
                .builder()
                .id(specificationGroup.getId())
                .specificationGroupName(specificationGroup.getSpecificationGroupName())
                .specificationDtos(specificationDtos)
                .build();
    }

    private SpecificationDto specificationMapToSpecificationDto(Specification specification) {
        List<ProductSpecificationDto> productSpecificationDtos = specification.getProductsSpecifications()
                .stream()
                .map(this::productSpecificationMapToProductSpecificationDto)
                .collect(Collectors.toList());
        return SpecificationDto
                .builder()
                .id(specification.getId())
                .specificationName(specification.getSpecificationName())
                .productSpecificationDtos(productSpecificationDtos)
                .build();
    }

    private ProductSpecificationDto productSpecificationMapToProductSpecificationDto(ProductsSpecification productsSpecification) {
        return ProductSpecificationDto
                .builder()
                .id(productsSpecification.getId())
                .productSpecificationName(productsSpecification.getProductSpecificationName())
                .build();
    }
}
