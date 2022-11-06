package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.*;
import com.api.mobigenz_be.entities.*;
import com.api.mobigenz_be.repositories.ProductLineRepository;
import com.api.mobigenz_be.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
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
    private ManufacturersService manufacturersService;
    @Autowired
    private OptionsService optionsService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SpecificationGroupService specificationGroupService;

    @Override
    public List<ProductDto> getProducts() {
        List<Product> products = this.productRepository.findAll();
        return products.stream().map(this::productMapToProductDto).collect(Collectors.toList());
    }

    @Transactional
    public ProductDto saveProduct(ProductDto productDto) {
        Product product = this.productRepository.save(this.productDtoMapToProduct(productDto));
        return this.productMapToProductDto(product);
    }

    @Transactional
    public ProductDto getProductDtoById(Integer id) {
        Optional<Product> productDtoOptional = this.productRepository.findById(id);
        return productDtoOptional.isPresent() ? this.productMapToProductDto(productDtoOptional.get()) : new ProductDto();
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
                .images(String.join(", ", productDto.getImages()))
                .status(1)
                .id(productDto.getId())
                .detail(productDto.getDetail())
                .description(productDto.getDescription())
                .productName(productDto.getProductName())
                .build();

        List<ProductsSpecificationGroup> productsSpecificationGroups = productDto.getSpecificationGroupDtos()
                .stream()
                .map(specificationGroupDto -> {
            ProductsSpecificationGroup productsSpecificationGroup = ProductsSpecificationGroup
                    .builder()
                    .specificationGroup(this.modelMapper.map(specificationGroupDto, SpecificationGroup.class))
                    .product(product)
                    .id(specificationGroupDto.getProductSpecificationGroupId())
                    .build();
            List<ProductsSpecification> productsSpecifications = specificationGroupDto.getSpecificationDtos().stream().map(specificationDto ->
                    ProductsSpecification
                            .builder()
                            .id(specificationDto.getProductSpecificationId())
                            .productSpecificationGroup(productsSpecificationGroup)
                            .specification(this.modelMapper.map(specificationDto, Specification.class))
                            .productSpecificationName(specificationDto.getProductSpecificationDtos().getProductSpecificationName())
                            .build()
            ).collect(Collectors.toList());
            productsSpecificationGroup.setProductsSpecifications(productsSpecifications);
            return productsSpecificationGroup;
        }).collect(Collectors.toList());

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
//                                        .id(productDetailDto.getProductVariantCombineId())
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
//                    .id(productDetailDto.getId())
                    .productVariantCombinationList(variantCombinations)
                    .price(productDetailDto.getPrice())
                    .sku(productDetailDto.getSku())
                    .image(productDetailDto.getImage())
                    .note(productDetailDto.getNote())
                    .product(product)
                    .build();
            productDetails.add(productDetail);
        });

        product.setProductsOptions(productsOptions);
        product.setProductDetails(productDetails);
        product.setProductsSpecificationGroups(productsSpecificationGroups);
        return product;
    }

    private ProductsOption optionDtoMapToProductsOption(OptionDto optionDto, List<ProductDetailDto> productDetailDtos) {
        ProductsOption productsOption = ProductsOption
                .builder()
                .option(modelMapper.map(optionDto, Option.class))
                .id(optionDto.getProductsOptionsId())
                .build();
        List<ProductsVariant> productsVariants = optionDto.getOptionValueDtos()
                .stream()
                .map(optionValueDto -> ProductsVariant
                        .builder()
                        .id(optionValueDto.getProductVariantsId())
                        .optionValue(this.modelMapper.map(optionValueDto, OptionsValue.class))
                        .productOption(productsOption)
                        .build()
                ).collect(Collectors.toList());
        productsOption.setProductsVariants(productsVariants);
        return productsOption;
    }

    private ProductDto productMapToProductDto(Product product) {
        ProductLineDto productLineDto = this.productLineRepository.getProductLineByProductId(product.getId());
        ManufacturerDto manufacturerDto = this.manufacturersService.getManufacturerByProductLineId(productLineDto.getId());
        List<ProductDetailDto> productDetailDtos = product.getProductDetails().stream().map(this::productDetailMapToProductDetailDto).collect(Collectors.toList());
        List<OptionDto> optionDtos = this.optionsService.getOptionsByProductId(product.getId());
        List<SpecificationGroupDto> specificationGroupDtos = this.specificationGroupService.getSpecificationGroupByProductId((product.getId()));
        return ProductDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .ctime(product.getCtime())
                .mtime(product.getMtime())
                .detail(product.getDetail())
                .images(Arrays.asList(product.getImages().split(", ")))
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
