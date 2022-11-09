package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.AccountDTO;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.entities.Account;
import com.api.mobigenz_be.entities.ResponseObject;
import com.api.mobigenz_be.repositories.AccountRepository;
import com.api.mobigenz_be.services.AccountService;
import com.api.mobigenz_be.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    private boolean check = true;

    private static Map<String,String> listOtp = new HashMap<>();

    @Autowired
    private AccountService accountService;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("mobilegenz/login")
    public Boolean login(@RequestParam String email, @RequestParam String password) {
        Optional<Account> account = this.accountRepository.getAccountLogin(email, password);
        if (account.isPresent()) return true;
        return false;
    }

    @PostMapping("mobilegenz/register")
    public ResponseEntity<ResponseDTO> insert(@RequestBody Account account) {
        try {
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
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("forgot/{email}")
    public ResponseEntity<ResponseObject> forgot(@PathVariable String email, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            Account account = this.accountRepository.findAccountByEmail(email);
            if (account.getEmail().equals(email)) {
                Random random = new Random();
                String otp = String.valueOf(random.nextInt(900000) + 100000);
                emailSenderService.sendSimpleEmail(email, "OTP code", "Your OTP code is: " + otp);
                session.setAttribute("OTP", otp);
                listOtp.put("email", account.getEmail());
                listOtp.put("otp", otp);
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("true", "OTP da duoc gui den email cua ban!", session)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("false", "Email ko ton tai!", email)
            );
        }
    }

    @GetMapping("getOTP")
    public ResponseEntity<ResponseObject> getOtp(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object isOtp = session.getAttribute("OTP");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("true", "OTP la: ", isOtp)
        );
    }
}
