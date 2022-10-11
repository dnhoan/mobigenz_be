package com.api.mobigenz_be.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "manufacturers")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "manufacturer_name", nullable = false, length = 100)
    private String manufacturerName;

    @Column(name = "ctime", nullable = false)
    private Instant ctime;

    @Column(name = "mtime")
    private Instant mtime;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "manufacturer")
    private Set<ProductLine> productLines = new LinkedHashSet<>();

}