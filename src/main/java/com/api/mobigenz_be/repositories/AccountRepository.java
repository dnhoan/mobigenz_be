package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.DTOs.PageDTO;
import com.api.mobigenz_be.entities.Account;
import com.api.mobigenz_be.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("select acc from Account acc order by acc.id")
    Page<Account> getAllById(Pageable pageable);

    @Query("select acc from Account acc where acc.phoneNumber = :valueSearch" +
            " or (lower(acc.email) like  '%' || lower(:valueSearch) || '%') ")
    Page<Account> findByKey(Pageable pageable, @Param("valueSearch") String valueSearch);

    @Query("select acc from Account acc where  acc.id = :id")
    Account findAccountById(Integer id);

    @Query("select acc from Account acc where  lower(acc.email) = lower(:email) ")
    Account findAccountByEmail(@Param("email") String email);

    @Query("select acc from Account acc where  acc.email = :email or acc.phoneNumber = :phoneNumber")
    Account findAccountByEmailorPhone(@Param("email") String email,@Param("phoneNumber") String phoneNumber);

    @Query("SELECT a from Account a where a.email= ?1 and a.password = ?2 ")
    Optional<Account> getAccountLogin(String email, String password);

    @Query("select a from Account a join Employee e on a = e.account where  e.id =:account")
    Account getAccountByEmployeeId(@Param("account")Integer account);

    @Query("select acc from Account acc join Customer cus on acc = cus.account where cus.id =:customer")
    Account getAccountByCustomer(@Param("customer") Integer customer);



}
