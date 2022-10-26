package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.*;
import com.api.mobigenz_be.entities.*;
import com.api.mobigenz_be.repositories.ManufacturersRepository;
import com.api.mobigenz_be.repositories.ProductLineRepository;
import com.api.mobigenz_be.repositories.ProductOptionRepository;
import com.api.mobigenz_be.repositories.ProductRepository;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
//@Transactional
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
    @Autowired
    private SpecificationGroupService specificationGroupService;
    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Override
    public List<ProductDto> getProducts() {
        List<Product> products = this.productRepository.findAll();
        return products.stream().map(this::productMapToProductDto).collect(Collectors.toList());
    }

    @Override
    public List<Product> getProducts2() {
        return null;
    }

    public ProductDto insertProduct(ProductDto productDto) {
        Product product = this.productRepository.save(this.productDtoMapToProduct(productDto));
        return this.productMapToProductDto(product);
    }

    private Product productDtoMapToProduct(ProductDto productDto) {

        ProductLine productLine = modelMapper.map(productDto.getProductLineDto(), ProductLine.class);

        List<ProductsOption> productsOptions = productDto.getOptionDtos()
                .stream()
                .map(optionDto -> this.optionDtoMapToProductsOption(optionDto, productDto.getProductDetailDtos()))
                .collect(Collectors.toList());


        Product product = Product
                .builder()
                .productLine(productLine)
                .ctime(LocalDateTime.now())
                .status(1)
                .description(productDto.getDescription())
                .productName(productDto.getProductName())
                .build();

        Set<ProductDetail> productDetails = new HashSet<>();
        List<ProductVariantCombination> productVariantCombinations = new ArrayList<>();

        productsOptions.forEach(productsOption -> {
            productsOption.getProductsVariants().forEach(productsVariant -> {
                productDto.getProductDetailDtos().forEach(productDetailDto -> {
                    if (productDetailDto.getProductVariantCombinationDtos()
                            .stream()
                            .anyMatch(productVariantCombinationDto -> productVariantCombinationDto.getOptionValueDto().getId() == productsVariant.getOptionValue().getId())) {
                        productVariantCombinations.add(
                                ProductVariantCombination
                                        .builder()
                                        .productVariant(productsVariant)
                                        .sku(productDetailDto.getSku())
                                        .build()
                        );
                    }
                });
            });
        });
        productDto.getProductDetailDtos().forEach(productDetailDto -> {
            List<ProductVariantCombination> variantCombinations = productVariantCombinations.stream().filter(productVariantCombination ->
                    productDetailDto.getProductVariantCombinationDtos().stream().anyMatch(productVariantCombinationDto ->
                            Objects.equals(productVariantCombinationDto.getOptionValueDto().getId(), productVariantCombination.getProductVariant().getOptionValue().getId()) &&
                                    productDetailDto.getSku().equalsIgnoreCase(productVariantCombination.getSku())
                    )
            ).collect(Collectors.toList());
            ProductDetail productDetail = ProductDetail
                    .builder()
                    .productVariantCombinationList(variantCombinations)
                    .price(productDetailDto.getPrice())
                    .sku(productDetailDto.getSku())
                    .image(productDetailDto.getImage())
                    .note(productDetailDto.getNote())
                    .product(product)
                    .build();
            productDetails.add(productDetail);
        });

        product.setProductDetails(productDetails);
        product.setProductsOptions(productsOptions);
        return product;
    }

    private ProductsOption optionDtoMapToProductsOption(OptionDto optionDto, List<ProductDetailDto> productDetailDtos) {

        List<ProductsVariant> productsVariants = optionDto.getOptionValueDtos()
                .stream()
                .map(optionValueDto -> ProductsVariant
                        .builder()
                        .optionValue(this.modelMapper.map(optionValueDto, OptionsValue.class))
                        .build()
                ).collect(Collectors.toList()); // Size: 4

//        List<ProductDetail> productDetails = new ArrayList<>();
//
//        List<ProductsVariant> finalProductsVariants = productsVariants;
//        productDetailDtos.forEach(productDetailDto -> { // Loop: 4
//            List<ProductsVariant> productsVariantListForProductDetail = finalProductsVariants
//                    .stream() // Loop: 4
//                    .filter(productsVariant -> productDetailDto.getProductVariantCombinationDtos() // Size: 2
//                            .stream() // Loop: 2
//                            .anyMatch(productVariantCombinationDto ->
//                                    Objects.equals(productVariantCombinationDto.getOptionValueDto().getId(), productsVariant.getOptionValue().getId())
//                            )
//                    ).collect(Collectors.toList());
//
//            ProductDetail productDetail = ProductDetail
//                    .builder()
//                    .productsVariants(productsVariantListForProductDetail)
//                    .price(productDetailDto.getPrice())
//                    .sku(productDetailDto.getSku())
//                    .image(productDetailDto.getImage())
//                    .note(productDetailDto.getNote())
//                    .build();
//            productDetails.add(productDetail);
////            }
//        });
//
//        productsVariants = productsVariants.stream().map(productsVariant -> {
//            List<ProductDetail> productDetailForProductVariant = productDetails
//                    .stream() // loop: 4
//                    .filter(productDetail ->
//                            productDetail.getProductsVariants()
//                                    .stream()// loop 2
//                                    .anyMatch(productsVariant1 ->
//                                            Objects.equals(productsVariant.getOptionValue().getId(), productsVariant1.getOptionValue().getId())
//                                    )
//                    ).collect(Collectors.toList());
//            productsVariant.setProductDetails(productDetailForProductVariant); // 2
//            return productsVariant;
//        }).collect(Collectors.toList());

        return ProductsOption.builder()
                .option(modelMapper.map(optionDto, Option.class))
                .productsVariants(productsVariants)
                .build();
    }

    private ProductDto productMapToProductDto(Product product) {
        ProductLineDto productLineDto = this.productLineRepository.getProductLineByProductId(product.getId());
        ManufacturerDto manufacturerDto = this.manufacturersRepository.getManufacturerByProductLineId(product.getId());
        List<ProductDetailDto> productDetailDtos = product.getProductDetails().stream().map(this::productDetailMapToProductDetailDto).collect(Collectors.toList());
        List<OptionDto> optionDtos = this.optionsService.getOptionsByProductId(product.getId());
        List<SpecificationGroupDto> specificationGroupDtos = this.specificationGroupService.getSpecificationGroupByProductId((product.getId()));
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
                .specificationGroupDtos(specificationGroupDtos)
                .build();
    }


    private ProductDetailDto productDetailMapToProductDetailDto(ProductDetail productDetail) {
        List<ProductVariantCombinationDto> productVariantCombinationDtos = productDetail.getProductVariantCombinationList().stream().map(this::productVariantCombinationMapToProductVariantCombinationDto).collect(Collectors.toList());
        return ProductDetailDto
                .builder()
                .id(productDetail.getId())
                .price(productDetail.getPrice())
                .sku(productDetail.getSku())
                .stock(productDetail.getStock())
                .image(productDetail.getImage())
                .note(productDetail.getNote())
                .status(productDetail.getStatus())
                .productVariantCombinationDtos(productVariantCombinationDtos)
                .build();

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
