package com.api.mobigenz_be.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "products_specification_groups")
public class ProductsSpecificationGroup {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Cascade(org.hibernate.annotations.CascadeType.LOCK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "specification_group_id")
//    private List<Specification> specifications;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "product_specification_group_id")
    private List<ProductsSpecification> productsSpecifications;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specification_group_id")
    private SpecificationGroup specificationGroup;

}