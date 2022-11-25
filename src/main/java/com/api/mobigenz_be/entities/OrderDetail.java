package com.api.mobigenz_be.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter@Builder
@Entity
@Table(name = "order_details")
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 131087011953212977L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;



    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.LOCK)
    @JoinColumn(name = "product_detail_id", nullable = false)
    private ProductDetail productDetail;

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.LOCK)
    @JoinColumn(name = "order_detail_id")
    private List<Imei> imeis;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "price_sell")
    private Double priceSell;

    @Column(name = "product_price")
    private Double productPrice;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "ctime", nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime ctime = LocalDateTime.now();

    @Column(name = "mtime")
    private LocalDateTime mtime;

    @Column(name = "note")
    @Type(type = "org.hibernate.type.TextType")
    private String note;

}