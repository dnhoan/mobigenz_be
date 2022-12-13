package com.api.mobigenz_be.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "employee_name", nullable = false, length = 100)
    private String employeeName;

    @Column(name = "employee_code", nullable = false, length = 100)
    private String employeeCode;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "phone_number", nullable = false, length = 12)
    private String phoneNumber;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "image")
    @Type(type = "org.hibernate.type.TextType")
    private String image;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "cmnd_cccd", length = 20)
    private String cmnd;

    @Column(name = "salary")
    private Float salary;

    @Column(name = "time_onboard")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate timeOnboard;

    @Column(name = "day_off")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dayOff;

    @Column(name = "note")
    @Type(type = "org.hibernate.type.TextType")
    private String note;

    @Column(name = "status")
    private Integer status;

}
