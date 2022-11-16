package com.api.mobigenz_be.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "phonenumber", nullable = false, length = 15)
    private String phoneNumber;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role roleid;

    @Column(name = "ctime", nullable = false)
    private LocalDateTime ctime;

    @Column(name = "mtime")
    private LocalDateTime mtime;

    @Column(name = "status")
    private Integer status;

}