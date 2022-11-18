package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("select acc from Account acc order by acc.id")
    Page<Account> getAllById(Pageable pageable);

    @Query("select acc from Account acc where  acc.id = :id")
    Account findAccountById(Integer id);

    @Query("select acc from Account acc where  acc.email = :email")
    Account findAccountByEmail(@Param("email") String email);

    @Query("SELECT a from Account a where a.email= ?1 and a.password = ?2 ")
    Optional<Account> getAccountLogin(String email, String password);

    @Query("select a from Account a join Employee e on a = e.account where  e.id =:account")
    Account getAccountByEmployeeId(@Param("account")Integer account);

}
