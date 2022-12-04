package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.ProductSpecificationDto;
import com.api.mobigenz_be.DTOs.SpecificationDto;
import com.api.mobigenz_be.DTOs.SpecificationGroupDto;
import com.api.mobigenz_be.entities.ProductsSpecification;
import com.api.mobigenz_be.entities.Specification;
import com.api.mobigenz_be.entities.SpecificationGroup;
import com.api.mobigenz_be.repositories.ProductSpecificationRepository;
import com.api.mobigenz_be.repositories.SpecificationGroupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecificationGroupServiceImp implements SpecificationGroupService {

    @Autowired
    private SpecificationGroupRepository specificationGroupRepository;
    @Autowired
    private SpecificationService specificationService;
    @Autowired
    private ProductSpecificationRepository productSpecificationRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<SpecificationGroupDto> getSpecificationGroupByProductId(Integer product_id) {
        return this.specificationGroupRepository.getSpecificationGroupByProductId((product_id))
                .stream()
                .map(specificationGroup ->  this.specificationGroupMapToSpecificationGroupDto(specificationGroup, product_id))
                .collect(Collectors.toList());
    }

    @Override
    public List<SpecificationGroupDto> getSpecificationGroup() {
        return this.specificationGroupRepository.findAll()
                .stream()
                .map(this::specificationGroupMapToSpecificationGroupDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public SpecificationGroupDto insertSpecificationGroup(String specificationGroupName){
        SpecificationGroup specificationGroup = SpecificationGroup.builder().specificationGroupName(specificationGroupName).build();
        specificationGroup.setCtime(LocalDateTime.now());
        specificationGroup = this.specificationGroupRepository.save(specificationGroup);
        return this.modelMapper.map(specificationGroup, SpecificationGroupDto.class);
    }


    private SpecificationGroupDto specificationGroupMapToSpecificationGroupDto(SpecificationGroup specificationGroup) {
        List<SpecificationDto> specificationDtos = this.specificationService.getSpecificationsBySpecificationGroupId(specificationGroup.getId())
                .stream()
                .map(specification -> this.specificationMapToSpecificationDto(specification, null))
                .collect(Collectors.toList());
        return SpecificationGroupDto
                .builder()
                .id(specificationGroup.getId())
                .specificationGroupName(specificationGroup.getSpecificationGroupName())
                .specificationDtos(specificationDtos)
                .build();
    }
    private SpecificationGroupDto specificationGroupMapToSpecificationGroupDto(SpecificationGroup specificationGroup, Integer product_id) {
        List<SpecificationDto> specificationDtos = this.specificationService.getSpecificationsBySpecificationGroupIdAndProductId(specificationGroup.getId(),product_id)
                .stream()
                .map(specification -> this.specificationMapToSpecificationDto(specification, product_id))
                .collect(Collectors.toList());
        return SpecificationGroupDto
                .builder()
                .id(specificationGroup.getId())
                .specificationGroupName(specificationGroup.getSpecificationGroupName())
                .specificationDtos(specificationDtos)
                .build();
    }

    private SpecificationDto specificationMapToSpecificationDto(Specification specification, Integer product_id) {
        List<ProductSpecificationDto> productSpecificationDtos =
                this.productSpecificationRepository.findProductsSpecificationBySpecificationIdAndProductId(specification.getId(), product_id)
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

    private SpecificationGroup specificationGroupDtoMapToSpecification(SpecificationGroupDto specificationGroupDto){

        SpecificationGroup specificationGroup = SpecificationGroup
                .builder()
                .specificationGroupName(specificationGroupDto.getSpecificationGroupName())
                .specifications(new ArrayList<>())
                .ctime(LocalDateTime.now())
                .build();
        return specificationGroup;
    }


}
