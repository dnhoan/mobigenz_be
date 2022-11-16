package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.AccountDTO;
import com.api.mobigenz_be.DTOs.PageDTO;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.entities.Account;
import com.api.mobigenz_be.services.AccountService;
import com.api.mobigenz_be.services.AccountServiceImpl;
import com.api.mobigenz_be.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @GetMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody String email, @RequestBody String phoneNumber, @RequestBody String password) {
//        if()
        return null;
    }

    @GetMapping("account")
    public ResponseEntity<ResponseDTO> getPageAccount(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "20") int limit
    ) {
        PageDTO<AccountDTO> items = this.accountService.getAll(offset, limit);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)

                        .data(Map.of("accounts", items))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }


    @PostMapping("account")
    public ResponseEntity<ResponseDTO> insert(@RequestBody Account account) {
        try {
            String password = account.getPassword();
            account.setPassword(passwordEncoder.encode(password));
            AccountDTO accountDTO = this.accountService.add(account);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("account", accountDTO))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("account")
    public ResponseEntity<ResponseDTO> update(@RequestBody Account account) {
        try {
            AccountDTO accountDTO = this.accountService.update(account);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)

                            .data(Map.of("account", accountDTO))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }


    @DeleteMapping("account/{id}")
    public ResponseEntity<ResponseDTO> deleteCustomer(@PathVariable("id") Integer id) {
        Account account = this.accountService.findById(id);
        if (account != null) {
            this.accountService.delete(account);

            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .message("Delete success")
                            .status(OK)
                            .data(Map.of("account", account))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()

            );
        }
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message("Customer id not exist")
                        .status(NO_CONTENT)
                        .statusCode(NO_CONTENT.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }
}
