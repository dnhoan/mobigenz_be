package com.api.mobigenz_be.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "specifications")
public class Specification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "specification_name", nullable = false, length = 100)
    private String specificationName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specification_group_id")
    private SpecificationGroup specificationGroup;

    @Column(name = "ctime", nullable = false)
    private Instant ctime;

    @Column(name = "mtime")
    private Instant mtime;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "specification")
    private Set<ProductsSpecification> productsSpecifications = new LinkedHashSet<>();

}