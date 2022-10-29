package com.api.mobigenz_be.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "products_specifications")
public class ProductsSpecification  {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.LOCK)
    @JoinColumn(name = "product_specification_group_id")
    private ProductsSpecificationGroup productSpecificationGroup;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.LOCK)
    @JoinColumn(name = "specification_id")
    private Specification specification;

    @Column(name = "product_specification_name", length = 100)
    private String productSpecificationName;

}