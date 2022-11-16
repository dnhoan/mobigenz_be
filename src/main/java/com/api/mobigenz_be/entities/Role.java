package com.api.mobigenz_be.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

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

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    Set<Permission> permissions;

}