package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.AccountDTO;
import com.api.mobigenz_be.DTOs.CustomerDTO;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.configs.securities.jwt.JWTFilter;
import com.api.mobigenz_be.configs.securities.jwt.TokenProvider;
import com.api.mobigenz_be.constants.Constant;
import com.api.mobigenz_be.controllers.admin.vm.LoginVM;
import com.api.mobigenz_be.entities.Account;
import com.api.mobigenz_be.entities.Customer;
import com.api.mobigenz_be.entities.ResponseObject;
import com.api.mobigenz_be.entities.Role;
import com.api.mobigenz_be.repositories.AccountRepository;
import com.api.mobigenz_be.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = Constant.Api.Path.PREFIX)
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticateController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

    public AuthenticateController(AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
    }

    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateAdmin(@Valid @RequestBody LoginVM loginVM) {
//		Tạo chuỗi authentication từ email và password (object LoginRequest
        try {
            Account account = this.accountRepository.findAccountByEmail(loginVM.getEmail());
            if (account != null) {
                UsernamePasswordAuthenticationToken authenticationString = new UsernamePasswordAuthenticationToken(
                        loginVM.getEmail(),
                        loginVM.getPassword()
                );
                Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationString);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = tokenProvider.createToken(authentication, loginVM.getRememberMe());
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, String.format("Bearer %s", jwt));
                System.out.println(jwt);
                return new ResponseEntity<>(Collections.singletonMap("token", jwt), httpHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return ResponseEntity.internalServerError().build();
//        User userLogin = userService.findUserByUserName(adminLoginVM.getUserName());
//        return ResponseEntity.ok().body(new JWTTokenResponse(jwt, userLogin.getUserName())); //Trả về chuỗi jwt(authentication string)

    }

    @PostMapping("register")
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
                        new ResponseObject("false", "Đăng ký thất bại!", "")
                );

            } else {
                String password = passwordEncoder.encode(account.getPassword());
                String email = account.getEmail();
                String name = account.getEmail().substring(0, email.indexOf("@"));
                Set<Role> roles = new HashSet<>();
                Role role = this.roleServiceImpl.getRoleById(1);
                roles.add(role);
                account.setRoles(roles);
                account.setPassword(password);
                account.setStatus(0);

                Customer customer = new Customer();
                customer.setCustomerName(name);
                customer.setAccount(account);
                customer.setPhoneNumber(account.getPhoneNumber());
                customer.setBirthday(LocalDate.parse("2000-01-01"));
                customer.setEmail(account.getEmail());
                customer.setStatus(1);


                customer.setCtime(LocalDate.now());

                account.setCustomer(customer);
//          CustomerDTO customerDTO = this.customerService.create(customer);
                AccountDTO accountDTO = this.accountService.add(account);
                return ResponseEntity.status(OK).body(
                        new ResponseObject("true", "Đăng ký tài khoản thành công!", accountDTO)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("forgot")
    public ResponseEntity<ResponseObject> forgot(@RequestParam(name = "email") String email, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Account account = this.accountRepository.findAccountByEmail(email);
        try {
            if (account == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("false", "Email ko ton tai!", "")
                );
            } else {
                if (email.equals(account.getEmail())) {
                    Random random = new Random();
                    int otp = random.nextInt(900000) + 100000;
                    emailSenderService.sendSimpleEmail(email, "Mã OTP", "Mã OTP của bạn là: " + otp);
                    session.setAttribute(email, otp);
                    System.out.println(session);
                }
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("true", "OTP đã được gửi đến email của bạn!", "")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("getOTP")
    public ResponseEntity<ResponseObject> getOtp(@RequestParam(name = "email") String email, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object isOtp = session.getAttribute(email);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("true", "OTP la: " + isOtp, "")
        );
    }
}
