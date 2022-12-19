package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("select e from Employee e order by e.id")
    Page<Employee> getAll(Pageable pageable);

    @Query("select e from Employee e where e.phoneNumber = :valueSearch" +
            " or (lower(e.email) like  '%' || lower(:valueSearch) || '%')" +
            " or (lower(e.employeeCode) like  '%' || lower(:valueSearch) || '%')" +
            " or (lower(e.employeeName) like  '%' || lower(:valueSearch) || '%')")
    Page<Employee> findByKey(Pageable pageable, @Param("valueSearch") String valueSearch);

    @Query("Select e from Employee  e where " +
            " e.phoneNumber =:search \n" +
            "or e.employeeName=:search \n" +
            " or e.email =:search \n" +
            "or e.address=:search \n" +
            "or e.employeeCode=:search")
    Page<Employee> findByAll(@Param("search") String search,Pageable pageable);



    @Query("select e from Employee e where  e.email = :email or e.phoneNumber = :phoneNumber")
    Employee findEmployeeByEmailorPhone(@Param("email") String email,@Param("phoneNumber") String phoneNumber);


    Employee findEmployeeById(Integer id);

    @Query("SELECT e from Employee e where e.account.id = :accountId")
    Employee findByAccountId(Integer accountId);

    @Query("SELECT e from Employee e where e.employeeName = :employeeName")
    Employee findByEmployeeName(@Param("employeeName") String employeeName);

    @Query("SELECT e from Employee e where e.email = :email")
    Employee findByEmail(@Param("email") String email);

}
