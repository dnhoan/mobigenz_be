package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.ProductLineDto;
import com.api.mobigenz_be.entities.Manufacturer;
import com.api.mobigenz_be.entities.ProductLine;
import com.api.mobigenz_be.repositories.ProductLineRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductLineServiceImp implements ProductLineService{

    @Autowired
    private ProductLineRepository productLineRepository;

    @Autowired
    private ProductLineService productLineService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductLineDto> getProductLine() {
        List<ProductLine> productLines = this.productLineRepository.findAll();


        return productLines.stream().map(this::productLineMapToProductLineDto).collect(Collectors.toList());
    }

    @Transactional
    public ProductLineDto saveProductLine(Integer manufacturerId, ProductLineDto productLineDto) {
        ProductLine productLine = this.productLineDtoMapToProductLine(productLineDto);
        productLine.setManufacturer(Manufacturer.builder().id(manufacturerId).build());
        productLine = this.productLineRepository.saveAndFlush(productLine);
        return this.modelMapper.map(productLine, ProductLineDto.class);
    }

    private ProductLineDto productLineMapToProductLineDto(ProductLine productLine){
        return ProductLineDto.builder()
                .id(productLine.getId())
                .productLineName(productLine.getProductLineName())
                .ctime(productLine.getCtime())
                .mtime(productLine.getMtime())
                .status(productLine.getStatus())
                .build();

    }

    private ProductLine productLineDtoMapToProductLine(ProductLineDto productLineDto){
        ProductLine productLine = ProductLine
                .builder()
                .productLineName(productLineDto.getProductLineName())
                .ctime(LocalDateTime.now())
                .status(1)
                .id(productLineDto.getId())
                .build();

        return productLine;
    }

}
