package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("select r from Role r where r.id = :id")
    Role getRoleById(@Param("id") Integer id);

}
