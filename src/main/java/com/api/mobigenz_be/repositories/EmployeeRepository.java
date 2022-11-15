package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.entities.Customer;
import com.api.mobigenz_be.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("Select e from Employee  e where " +
            " e.phoneNumber =:search \n" +
            "or e.employeeName=:search \n" +
            " or e.email =:search \n" +
            "or e.address=:search \n" +
            "or e.employeeCode=:search")
    List<Employee> findByAll(@Param("search") String search);



}
