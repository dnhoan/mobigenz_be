package com.api.mobigenz_be.DTOs;

import com.api.mobigenz_be.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Integer id;
    private String email;
    private String password;
    private String phoneNumber;
    private Integer status;
    private LocalDateTime ctime;
    private Role roleid;
}
