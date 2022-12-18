package com.api.mobigenz_be.entities;

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
@Getter@Builder
@Setter
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    private static final long serialVersionUID = 366892926716629503L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_detail_id")
    private List<OrderDetail> orderDetails ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "recipient_name", length = 100)
    private String recipientName;

    @Column(name = "recipient_phone", length = 100)
    private String recipientPhone;

    @Column(name = "recipient_email", length = 100)
    private String recipientEmail;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "total_money")
    private Double totalMoney;

    @Column(name = "ship_fee")
    private Double shipFee;

    @Column(name = "goods_value")
    private Double goodsValue;

    @Column(name = "checkout")
    private Double checkout;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "delivery")
    private Integer delivery;

    @Column(name = "ship_date")
    private LocalDateTime shipDate;

    @Column(name = "carrier", length = 100)
    private String carrier;

    @Column(name = "ctime", nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime ctime = LocalDateTime.now();

    @Column(name = "mtime")
    private LocalDateTime mtime;

    @Column(name = "pay_status")
    private Integer payStatus;

    @Column(name = "order_status")
    private Integer orderStatus;

    @Column(name = "purchasetype")
    private Integer purchaseType;

    @Column(name = "cancel_note")
    private String cancelNote;

    @Column(name = "note")
    @Type(type = "org.hibernate.type.TextType")
    private String note;

}