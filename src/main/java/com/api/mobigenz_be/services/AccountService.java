package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.AccountDTO;
import com.api.mobigenz_be.DTOs.PageDTO;
import com.api.mobigenz_be.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface AccountService {

    PageDTO<AccountDTO> getAll(int offset, int limit);

    AccountDTO add(Account account);

    AccountDTO update(Account account);

    AccountDTO delete(Account account);

    Account findById(Integer id);

//    List<Account> findAll();

    Account findByEmail(String email);

    Page<Account> findByKey(Pageable pageable, String valueSearch);

    Optional<Account> getAccountLogin(String email, String password);

}
