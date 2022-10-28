package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.*;
import com.api.mobigenz_be.entities.Product;
import com.api.mobigenz_be.entities.ProductDetail;
import com.api.mobigenz_be.entities.ProductVariantCombination;
import com.api.mobigenz_be.repositories.ManufacturersRepository;
import com.api.mobigenz_be.repositories.ProductLineRepository;
import com.api.mobigenz_be.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductLineRepository productLineRepository;
    @Autowired
    private ManufacturersRepository manufacturersRepository;
    @Autowired
    private OptionsService optionsService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductDto> getProducts() {
        List<Product> products = this.productRepository.findAll();
        return products.stream().map(this::productMapToProductDto).collect(Collectors.toList());
    }

    @Override
    public List<Product> getProducts2() {
        return null;
    }

    private ProductDto productMapToProductDto(Product product) {
        ProductLineDto productLineDto = this.productLineRepository.getProductLineByProductId(product.getId());
        ManufacturerDto manufacturerDto = this.manufacturersRepository.getManufacturerByProductLineId(product.getId());
        List<ProductDetailDto> productDetailDtos = product.getProductDetails().stream().map(this::productDetailMapToProductDetailDto).collect(Collectors.toList());
        List<OptionDto> optionDtos = this.optionsService.getOptionsByProductId(product.getId());
        return ProductDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .ctime(product.getCtime())
                .mtime(product.getMtime())
                .manufacturerDto(manufacturerDto)
                .productLineDto(productLineDto)
                .status(product.getStatus())
                .productDetailDtos(productDetailDtos)
                .optionDtos(optionDtos)
                .build();
    }

    private ProductDetailDto productDetailMapToProductDetailDto(ProductDetail productDetail) {
        List<ProductVariantCombinationDto> productVariantCombinationDtos = productDetail.getProductVariantCombinationList().stream().map(this::productVariantCombinationMapToProductVariantCombinationDto).collect(Collectors.toList());
        return new ProductDetailDto(
                productDetail.getId(),
                productDetail.getPrice(),
                productDetail.getSku(),
                productDetail.getStock(),
                productDetail.getImage(),
                productDetail.getNote(),
                productDetail.getStatus(),
                productVariantCombinationDtos
        );
    }

    private ProductVariantCombinationDto productVariantCombinationMapToProductVariantCombinationDto(ProductVariantCombination productVariantCombination) {
        OptionDto optionDto = this.modelMapper.map(
                productVariantCombination.getProductVariant().getProductOption().getOption(), OptionDto.class
        );
        OptionValueDto optionValueDto = this.modelMapper.map(
                productVariantCombination.getProductVariant().getOptionValue(), OptionValueDto.class
        );
        return new ProductVariantCombinationDto(optionDto, optionValueDto);
    }
}
