package com.api.mobigenz_be.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "total_money")
    private Double totalMoney;

    @Column(name = "payment_method")
    private Integer paymentMethod;

    @Column(name = "ctime", nullable = false)
    private Instant ctime;

    @Column(name = "note")
    @Type(type = "org.hibernate.type.TextType")
    private String note;

}