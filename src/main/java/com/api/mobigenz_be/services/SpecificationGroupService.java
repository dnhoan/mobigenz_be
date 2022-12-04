package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.SpecificationGroupDto;

import java.util.List;

public interface SpecificationGroupService {

    List<SpecificationGroupDto> getSpecificationGroupByProductId(Integer product_id);

    List<SpecificationGroupDto> getSpecificationGroup();

    SpecificationGroupDto insertSpecificationGroup (String specificationGroupName);
}
