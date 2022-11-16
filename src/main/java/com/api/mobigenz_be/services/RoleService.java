package com.api.mobigenz_be.services;

import com.api.mobigenz_be.entities.Account;
import com.api.mobigenz_be.entities.Role;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleService {
    List<Role> getAll();

    Role getRoleById(Integer id);

}
