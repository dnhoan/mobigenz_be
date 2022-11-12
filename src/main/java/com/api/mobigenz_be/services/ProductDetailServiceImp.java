package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.OptionDto;
import com.api.mobigenz_be.DTOs.OptionValueDto;
import com.api.mobigenz_be.DTOs.ProductDetailDto;
import com.api.mobigenz_be.DTOs.ProductVariantCombinationDto;
import com.api.mobigenz_be.entities.ProductDetail;
import com.api.mobigenz_be.entities.ProductVariantCombination;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDetailServiceImp implements ProductDetailService{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDetailDto saveProductDetailDto(ProductDetailDto productDetailDto) {
        return null;
    }

    public ProductDetailDto productDetailMapToProductDetailDto(ProductDetail productDetail) {
        List<ProductVariantCombinationDto> productVariantCombinationDtos = productDetail.getProductVariantCombinationList().stream().map(this::productVariantCombinationMapToProductVariantCombinationDto).collect(Collectors.toList());
        return ProductDetailDto
                .builder()
                .id(productDetail.getId())
                .price(productDetail.getPrice())
                .priceSell(productDetail.getPriceSell())
                .priceOrigin(productDetail.getPriceOrigin())
                .sku(productDetail.getSku())
                .stock(productDetail.getStock())
                .image(productDetail.getImage())
                .note(productDetail.getNote())
                .status(productDetail.getStatus())
                .productVariantCombinationDtos(productVariantCombinationDtos)
                .build();

    }

    private ProductVariantCombinationDto productVariantCombinationMapToProductVariantCombinationDto(ProductVariantCombination productVariantCombination) {
        OptionDto optionDto =
                this.modelMapper.map(
                        productVariantCombination.getProductVariant().getProductOption().getOption(), OptionDto.class
                );
        OptionValueDto optionValueDto = this.modelMapper.map(
                productVariantCombination.getProductVariant().getOptionValue(), OptionValueDto.class
        );
        return new ProductVariantCombinationDto(optionDto, optionValueDto);
    }
}
