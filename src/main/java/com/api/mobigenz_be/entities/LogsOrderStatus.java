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
@Table(name = "logs_order_status")
public class LogsOrderStatus implements Serializable {
    private static final long serialVersionUID = 5051901929609488229L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "new_status")
    private Integer newStatus;

    @Column(name = "\"time\"")
    private Instant time;

    @Column(name = "user_change", length = 50)
    private String userChange;

    @Column(name = "note")
    @Type(type = "org.hibernate.type.TextType")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "user_id")
    private Integer userId;

}