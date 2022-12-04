package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.ProductSpecificationDto;
import com.api.mobigenz_be.DTOs.SpecificationDto;
import com.api.mobigenz_be.entities.ProductsSpecification;
import com.api.mobigenz_be.entities.Specification;
import com.api.mobigenz_be.entities.SpecificationGroup;
import com.api.mobigenz_be.repositories.ProductSpecificationRepository;
import com.api.mobigenz_be.repositories.SpecificationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecificationServiceImp implements SpecificationService{

    @Autowired
    private SpecificationRepository specificationRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<Specification> getSpecificationsBySpecificationGroupIdAndProductId(Integer specification_group_id, Integer product_id) {
        return this.specificationRepository.getSpecificationsBySpecificationGroupIdAndProductId(specification_group_id, product_id);
    }
    @Override
    public List<Specification> getSpecificationsBySpecificationGroupId(Integer specification_group_id) {
        return this.specificationRepository.getSpecificationsBySpecificationGroupId(specification_group_id);
    }

    public List<SpecificationDto> getList() {
        return this.specificationRepository.findAll()
                .stream()
                .map(this::specificationMapToSpecificationDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SpecificationDto insertSpecification(Integer specification_group_id, String specification_name){
        Specification specification = Specification.builder().specificationName(specification_name).build();
        specification.setCtime(LocalDateTime.now());
        specification.setSpecificationGroup(SpecificationGroup.builder().id(specification_group_id).build());
        specification = this.specificationRepository.save(specification);
        return this.modelMapper.map(specification, SpecificationDto.class);

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
                .value(productSpecificationDtos.size() > 0 ? productSpecificationDtos.get(0).getProductSpecificationName() : "")
                .productSpecificationDtos(productSpecificationDtos.size() > 0 ? productSpecificationDtos.get(0) : new ProductSpecificationDto())
                .build();
    }

    private ProductSpecificationDto productSpecificationMapToProductSpecificationDto(ProductsSpecification productsSpecification) {
        return ProductSpecificationDto
                .builder()
                .id(productsSpecification.getId())
                .productSpecificationName(productsSpecification.getProductSpecificationName())
                .build();
    }

    private Specification SpecificationDtoMapToSpecification(SpecificationDto specificationDto){

        Specification specification = Specification
                .builder()
                .specificationName(specificationDto.getSpecificationName())
                .productsSpecifications(new ArrayList<>())
                .ctime(LocalDateTime.now())
                .build();

        return specification;
    }
}

