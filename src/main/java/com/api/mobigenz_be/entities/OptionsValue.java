package com.api.mobigenz_be.entities;

import com.api.mobigenz_be.DTOs.OptionDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "options_values")
public class OptionsValue{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "option_value_name", nullable = false, length = 100)
    private String optionValueName;

    @Column(name = "option_name", nullable = false, length = 100)
    private String optionName;

    @Lob
    @Column(name = "note")
    private String note;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    private Option optionId;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_value_id")
    private List<ProductsVariant> productsVariants;

    @Column(name = "ctime", nullable = false)
    private LocalDateTime ctime;

    @Column(name = "mtime")
    private LocalDateTime mtime;

    @Column(name = "status")
    private Integer status;

}