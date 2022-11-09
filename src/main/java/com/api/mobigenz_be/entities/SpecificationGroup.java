package com.api.mobigenz_be.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

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
@Table(name = "specification_groups")
public class SpecificationGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "specification_group_name", nullable = false, length = 100)
    private String specificationGroupName;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "specification_group_id")
    private List<Specification> specifications ;

    @Column(name = "ctime")
    private LocalDateTime ctime;

    @Column(name = "mtime")
    private LocalDateTime mtime;

    @Column(name = "status")
    private Integer status;

}