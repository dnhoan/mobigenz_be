package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.*;
import com.api.mobigenz_be.constants.Constant;
import com.api.mobigenz_be.entities.Account;
import com.api.mobigenz_be.entities.Customer;
import com.api.mobigenz_be.entities.ResponseObject;
import com.api.mobigenz_be.repositories.AccountRepository;
import com.api.mobigenz_be.services.AccountService;
import com.api.mobigenz_be.services.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("account/getAll")
    public ResponseEntity<ResponseDTO> getPageAccount(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "5") int limit
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
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("account/findByStatus")
    public ResponseEntity<ResponseDTO> findByStatus(
            @RequestParam(value = "offset", defaultValue = "")  int offset
            ,@RequestParam(value = "limit",defaultValue = "") int limit
            ,@RequestParam(value = "status",defaultValue = "") int status) {
        try {
            offset = offset < 0 ? 0 : offset;
            Pageable pageable;

            List<Sort.Order> orders = new ArrayList<>();
            pageable = PageRequest.of(offset, limit, Sort.by("status"));
            Page<Account> pageAccount = this.accountService.findByStatus(pageable, status);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("accounts", pageAccount))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    @PutMapping("account/findByKey")
    public ResponseEntity<ResponseDTO> findByKey(
             @RequestParam(value = "offset", defaultValue = "")  int offset
            ,@RequestParam(value = "limit",defaultValue = "") int limit
            ,@RequestBody SearchDTO searchDTO) {
        try {
            offset = offset < 0 ? 0 : offset;
            Pageable pageable;

            List<Sort.Order> orders = new ArrayList<>();
            List<ListSortDTO> listSortDTO = searchDTO.getListSortDTO();
            pageable = PageRequest.of(offset, limit, Sort.by("id"));
            Page<Account> pageAccount = this.accountService.findByKey(pageable, searchDTO.getValueSearch());
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("accounts", pageAccount))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("account/getAccountByEmail")
    public ResponseEntity<ResponseDTO> getAccountByEmail(@RequestParam(value = "email") String email) {
        try {
            Account account = this.accountServiceImpl.findByEmail(email);
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

    @GetMapping("account/getAccountById")
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


    @PostMapping("account/addAccount")
    public ResponseEntity<ResponseObject> insert(@RequestBody Account account) {
        try {
            Account acc = this.accountRepository.findAccountByEmailorPhone(account.getEmail(), account.getPhoneNumber());
            if (acc != null) {
                if (acc.getPhoneNumber().equalsIgnoreCase(account.getPhoneNumber())) {
                    return ResponseEntity.status(BAD_REQUEST).body(
                            new ResponseObject("false", "Số điện thoại đã tồn tại!", acc.getPhoneNumber())
                    );
                }
                if (acc.getEmail().equalsIgnoreCase(account.getEmail())) {
                    return ResponseEntity.status(BAD_REQUEST).body(
                            new ResponseObject("false", "Email đã tồn tại!", acc.getEmail())
                    );
                }
                return ResponseEntity.status(BAD_REQUEST).body(
                        new ResponseObject("false", "Thêm tài khoản thất bại!", "")
                );

            } else {
                String password = passwordEncoder.encode(account.getPassword());
                account.setPassword(password);
                AccountDTO accountDTO = this.accountService.add(account);
                return ResponseEntity.status(OK).body(
                        new ResponseObject("true", "Thêm tài khoản thành công!", accountDTO)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("account/updateAccount")
    public ResponseEntity<ResponseObject> update(@RequestBody Account account) {
        try {
            Account acc = this.accountRepository.checkAccount(account.getId(), account.getPhoneNumber());
            if (acc != null) {
                return ResponseEntity.status(BAD_REQUEST).body(
                        new ResponseObject("false", "Số điện thoại đã tồn tại!", acc.getPhoneNumber())
                );
            } else {
                Optional<Account> account1 = this.accountRepository.findById(account.getId());
                if (account1.isPresent()){
                    this.accountService.update(account);
                    List<AccountDTO> accountDTOList = this.accountService.getAllAcc();
                    return ResponseEntity.status(OK).body(
                            new ResponseObject("true", "Cập nhật khách hàng thành công!", accountDTOList)
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("account/changepass/{id}")
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


    @DeleteMapping("account/delete/{id}")
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
