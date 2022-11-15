package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.DTOs.AccountDTO;
import com.api.mobigenz_be.entities.Account;
import com.api.mobigenz_be.entities.Customer;
import com.api.mobigenz_be.entities.Role;
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

    @Query("select acc from Account acc where  acc.id = :id")
    Account findAccountById(Integer id);

    @Query("select acc from Account acc where  acc.email = :email")
    Account findAccountByEmail(String email);

    @Query("SELECT DISTINCT a FROM Account a WHERE a.roleid IN (Role)")
    List<Account> getAdministrators();

    @Query("SELECT a from Account a where a.email= :email and a.password = :password")
    Optional<Account> getAccountLogin(String email, String password);

    @Query("select a from Account a join Employee e on a = e.account where  e.id =:account")
    Account getAccountByEmployeeId(@Param("account")Integer account);

}
