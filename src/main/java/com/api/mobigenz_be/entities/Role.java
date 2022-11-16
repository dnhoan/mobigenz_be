package com.api.mobigenz_be.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role{
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "role_name", nullable = false, length = 50)
    private String roleName;

    @Column(name = "ctime", nullable = false)
    private LocalDateTime ctime;

    @Column(name = "mtime")
    private LocalDateTime mtime;

    @Column(name = "note")
    @Type(type = "org.hibernate.type.TextType")
    private String note;

}