package com.api.mobigenz_be.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class ProductDetailsVariantId implements Serializable {
    private static final long serialVersionUID = 7184707283786767823L;
    @Column(name = "product_detail_id", nullable = false)
    private Long productDetailId;

    @Column(name = "product_variant_id", nullable = false)
    private Long productVariantId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductDetailsVariantId entity = (ProductDetailsVariantId) o;
        return Objects.equals(this.productVariantId, entity.productVariantId) &&
                Objects.equals(this.productDetailId, entity.productDetailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productVariantId, productDetailId);
    }

}