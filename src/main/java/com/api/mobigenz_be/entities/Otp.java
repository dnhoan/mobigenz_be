package com.api.mobigenz_be.entities;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity(name = "Otp")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "otpCode")
    private Integer otpCode;

    @Column(name = "emailAccount")
    private String emailAccount;

    @Column(name = "issue_At")
    private Long issue_At;

    @Column(name = "status")
    private Integer status;
}
