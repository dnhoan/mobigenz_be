package com.api.mobigenz_be.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.utility.nullability.MaybeNull;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_details")
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "price")
    private Double price;

    @Column(name = "sku", length = 100)
    private String sku;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "image")
    @Type(type = "org.hibernate.type.TextType")
    private String image;

    @Column(name = "note")
    @Type(type = "org.hibernate.type.TextType")
    private String note;

    @Column(name = "status")
    private Integer status;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_DETAIL_ID")
    private List<ProductVariantCombination> productVariantCombinationList;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}