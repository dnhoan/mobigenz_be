package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.ManufacturerDto;
import com.api.mobigenz_be.DTOs.OptionDto;
import com.api.mobigenz_be.DTOs.ProductLineDto;
import com.api.mobigenz_be.entities.*;
import com.api.mobigenz_be.repositories.ManufacturersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManufacturersServiceImp implements ManufacturersService {

    @Autowired
    private ManufacturersRepository manufacturersRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ManufacturerDto> getList() {
        return this.manufacturersRepository
                .findAll()
                .stream()
                .map(this::manufacturerMapToManufacturerDto)
                .collect(Collectors.toList());
    }

    @Override
    public ManufacturerDto getManufacturerByProductLineId(Integer productLineId) {
        Manufacturer manufacturer = this.manufacturersRepository.getManufacturerByProductLineId(productLineId);
        return this.manufacturerMapToManufacturerDto(manufacturer);
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

    @Transactional
    public ManufacturerDto saveManufacturer(ManufacturerDto manufacturerDto){
       Manufacturer manufacturer = this.manufacturersRepository.save(manufacturerDtoMapToManufacturer(manufacturerDto));
       return this.manufacturerMapToManufacturerDto(manufacturer);


//        manufacturer.setProductLines(new ArrayList<>());
//        manufacturer.setCtime(LocalDateTime.now());
//        manufacturer.getManufacturerName();
//        System.out.println("balbla");
//        System.out.println(manufacturer.getManufacturerName());
//        return this.manufacturersRepository.saveAndFlush(manufacturer);
//        return this.modelMapper.map(manufacturer, ManufacturerDto.class);

    }

    private Manufacturer manufacturerDtoMapToManufacturer(ManufacturerDto manufacturerDto){
        Manufacturer manufacturer = Manufacturer
                .builder()
                .manufacturerName(manufacturerDto.getManufacturerName())
                .productLines(new ArrayList<>())
                .ctime(LocalDateTime.now())
                .build();

        System.out.println("balbla");
       System.out.println(manufacturer.getManufacturerName());

        return manufacturer;
    }



}
