package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.ManufacturerDto;
import com.api.mobigenz_be.DTOs.OptionDto;
import com.api.mobigenz_be.DTOs.ProductLineDto;
import com.api.mobigenz_be.entities.Manufacturer;
import com.api.mobigenz_be.entities.Option;
import com.api.mobigenz_be.entities.ProductLine;
import com.api.mobigenz_be.repositories.ManufacturersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManufacturersServiceImp implements ManufacturersService {

    @Autowired
    private ManufacturersRepository manufacturersRepository;

    public List<ManufacturerDto> getList() {
        return this.manufacturersRepository
                .findAll()
                .stream()
                .map(this::manufacturerMapToManufacturerDto)
                .collect(Collectors.toList());
    }

    private ManufacturerDto manufacturerMapToManufacturerDto(Manufacturer manufacturer) {
        List<ProductLineDto> productLineDtos = manufacturer.getProductLines()
                .stream()
                .map(this::optionMapToOptionDto)
                .collect(Collectors.toList());
        return ManufacturerDto
                .builder()
                .id(manufacturer.getId())
                .manufacturerName(manufacturer.getManufacturerName())
                .productLineDtos(productLineDtos)
                .build();
    }

    private ProductLineDto optionMapToOptionDto(ProductLine productLine) {
        return ProductLineDto
                .builder()
                .id(productLine.getId())
                .productLineName(productLine.getProductLineName())
                .build();
    }
}
