package com.api.mobigenz_be.entities;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "products_variants")
public class ProductsVariant  {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_value_id")
    private OptionsValue optionValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_option_id")
    private ProductsOption productOption;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "product_details_variants",
//            joinColumns = @JoinColumn(name = "product_variant_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_detail_id"))
//    private List<ProductDetail> productDetails = new ArrayList<>();

    @Cascade(org.hibernate.annotations.CascadeType.LOCK)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id")
    private List<ProductVariantCombination> productVariantCombinations;

}