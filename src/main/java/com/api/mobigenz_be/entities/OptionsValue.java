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
@Table(name = "options_values")
public class OptionsValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "option_value_name", nullable = false, length = 100)
    private String optionValueName;

    @Column(name = "option_name", nullable = false, length = 100)
    private String optionName;

    @Lob
    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option option;

    @Column(name = "ctime", nullable = false)
    private Instant ctime;

    @Column(name = "mtime")
    private Instant mtime;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "optionValue")
    private Set<ProductsVariant> productsVariants = new LinkedHashSet<>();

}