package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.AccountDTO;
import com.api.mobigenz_be.DTOs.PageDTO;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.constants.Constant;
import com.api.mobigenz_be.entities.Account;
import com.api.mobigenz_be.entities.ResponseObject;
import com.api.mobigenz_be.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = Constant.Api.Path.ACCOUNT)
@CrossOrigin("*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getPageAccount(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "20") int limit
    ) {
        try {
            PageDTO<AccountDTO> items = this.accountService.getAll(offset, limit);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("accounts", items))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("getAccountByEmail")
    public ResponseEntity<ResponseDTO> getAccountByEmail(@RequestParam(value = "email") String email) {
        try {
            Account account = this.accountService.findByEmail(email);
//            System.out.println(account.getCustomer().getCustomerName());
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("account", account))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("getAccountById")
    public ResponseEntity<ResponseDTO> getAccountById(@RequestParam(value = "id") Integer id) {
        try {
            Account account = this.accountService.findById(id);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("account", account))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping("addAccount")
    public ResponseEntity<ResponseDTO> insert(@RequestBody Account account) {
        try {
            String password = passwordEncoder.encode(account.getPassword());
            account.setPassword(password);
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


    @PutMapping("updateAccount")
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

    @PutMapping("changepass/{id}")
    public ResponseEntity<ResponseDTO> changePass(@PathVariable("id") Integer id, @RequestBody String password) {
        try {
            Account account = accountService.findById(id);
            account.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            accountService.update(account);
            System.out.println(account.getPassword());
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("account", account))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @DeleteMapping("delete/{id}")
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
