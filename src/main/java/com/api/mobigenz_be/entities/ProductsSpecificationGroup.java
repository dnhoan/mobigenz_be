package com.api.mobigenz_be.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products_specification_groups")
public class ProductsSpecificationGroup {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "specification_group_id")
    private List<Specification> specifications;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_specification_group_id")
    private List<ProductsSpecification> productsSpecifications;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specification_group_id")
    private SpecificationGroup specificationGroup;

}