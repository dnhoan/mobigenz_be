package com.api.mobigenz_be.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import net.bytebuddy.utility.nullability.MaybeNull;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_DETAIL_ID")
    private List<ProductVariantCombination> productVariantCombinationList = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDetail that = (ProductDetail) o;
        return Objects.equals(id, that.id) && Objects.equals(price, that.price) && Objects.equals(sku, that.sku) && Objects.equals(stock, that.stock) && Objects.equals(image, that.image) && Objects.equals(note, that.note) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, sku, stock, image, note, status);
    }

//    @ManyToMany(mappedBy = "productDetails", cascade = { CascadeType.ALL })
//    private List<ProductsVariant> productsVariants = new ArrayList<>();


}