package com.api.mobigenz_be.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers_address")
public class CustomersAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "customer_id")
    private Double customerId;

    @Column(name = "payment_method")
    private Integer paymentMethod;

    @Column(name = "ctime", nullable = false)
    private Instant ctime;

    @Column(name = "note")
    @Type(type = "org.hibernate.type.TextType")
    private String note;

}