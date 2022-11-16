package com.api.mobigenz_be.services;

import com.api.mobigenz_be.entities.Role;
import com.api.mobigenz_be.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return this.roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Integer id) {
        return this.roleRepository.getRoleById(id);
    }

}
