package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.ManufacturerDto;
import com.api.mobigenz_be.entities.Manufacturer;

import java.util.List;

public interface ManufacturersService {
    List<ManufacturerDto> getList();
    ManufacturerDto getManufacturerByProductLineId(Integer  ProductLineId);
}
