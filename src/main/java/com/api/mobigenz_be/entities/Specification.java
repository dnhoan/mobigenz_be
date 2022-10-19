package com.api.mobigenz_be.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "specifications")
public class Specification{
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "specification_name", nullable = false, length = 100)
    private String specificationName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specification_group_id")
    private SpecificationGroup specificationGroup;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_specification_group_id")
    private List<ProductsSpecification> productsSpecifications;

    @Column(name = "ctime", nullable = false)
    private LocalDateTime ctime;

    @Column(name = "mtime")
    private LocalDateTime mtime;

    @Column(name = "status")
    private Integer status;

}