package com.api.mobigenz_be.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "imei")
public class Imei {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "imei", nullable = false, length = 20)
    private String imei;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_option_id")
    private ProductsVariantsOption productVariantOption;

    @Column(name = "status")
    private Integer status;

}