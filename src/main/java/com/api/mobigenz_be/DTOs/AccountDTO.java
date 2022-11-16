package com.api.mobigenz_be.DTOs;

import com.api.mobigenz_be.entities.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Integer id;
    private String email;
    private String password;
    private String phoneNumber;
    private Integer status;
    private LocalDateTime ctime;
    private Set<Role> roles;
}
