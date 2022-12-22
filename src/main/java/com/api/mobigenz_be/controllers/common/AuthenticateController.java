package com.api.mobigenz_be.controllers.common;

import com.api.mobigenz_be.DTOs.AccountDTO;
import com.api.mobigenz_be.configs.securities.jwt.JWTFilter;
import com.api.mobigenz_be.configs.securities.jwt.TokenProvider;
import com.api.mobigenz_be.constants.Constant;
import com.api.mobigenz_be.controllers.admin.vm.LoginVM;
import com.api.mobigenz_be.entities.*;
import com.api.mobigenz_be.repositories.AccountRepository;
import com.api.mobigenz_be.services.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public JavaMailSender emailSender;

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

    @Autowired
    private OtpService otpService;


    @PostMapping("login")
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
                Role role = this.roleServiceImpl.getRoleById(3);
                roles.add(role);
                account.setRoles(roles);
                account.setPassword(password);
                account.setStatus(0);

                Customer customer = new Customer();
                customer.setCustomerName(name);
                customer.setAccount(account);
                customer.setPhoneNumber(account.getPhoneNumber());
                customer.setBirthday(LocalDate.of(1970, 1, 1));
                customer.setEmail(account.getEmail());
                customer.setCustomerType(0);
                customer.setStatus(1);
                customer.setCtime(LocalDate.now());

                account.setCustomer(customer);
                AccountDTO accountDTO = this.accountService.add(account);
                return ResponseEntity.status(OK).body(
                        new ResponseObject("true", "Đăng ký tài khoản thành công!", "")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("forgot")
    public ResponseEntity<ResponseObject> forgot(@RequestParam(value = "email") String email) {
        Account account = this.accountRepository.findAccountByEmail(email);
        try {
            if (account == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("false", "Email không có trong hệ thống!", "")
                );
            } else {
                ExecutorService executor = Executors.newFixedThreadPool(10);
                Random random = new Random();
                int otp = random.nextInt(900000) + 100000;
                SimpleMailMessage message = new SimpleMailMessage();

                message.setTo(email);
                message.setSubject("Mã OTP xác thực tài khoản:");
                message.setText("Mã OTP của bạn là: " + otp);
                Otp isOtp = new Otp();
                isOtp.setOtpCode(otp);
                isOtp.setEmailAccount(account.getEmail());
                isOtp.setStatus(1);
                isOtp.setIssue_At(System.currentTimeMillis() + 300000);
                Optional<Otp> otp1 = this.otpService.findByEmail(email);
                if (otp1.isPresent()) {
                    isOtp.setId(otp1.get().getId());
                }
                this.otpService.save(isOtp);

                CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> emailSender.send(message), executor);
                executor.shutdown();

                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("true", "OTP đã được gửi đến email của bạn!", email)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(BAD_REQUEST).body(
                    new ResponseObject("false", "Lấy mã OTP thất bại!", "")
            );
        }
    }

//    @GetMapping("getOTP")
//    public ResponseEntity<ResponseObject> getOTP(@RequestParam(value = "email") String email) {
//        Object isOtp = session.getAttribute(email);
//        return ResponseEntity.status(OK).body(
//                new ResponseObject("true", "OTP la: " + isOtp, isOtp)
//        );
//    }


    @GetMapping("changepass")
    public ResponseEntity<ResponseObject> changePass(@RequestParam(value = "email") String email,
                                                     @RequestParam(value = "isOtp") String otp,
                                                     @RequestParam(value = "password") String password,
                                                     @RequestParam(value = "repassword") String repassword
    ) {
        try {
            Optional<Otp> isOtp = this.otpService.findByEmail(email);
            if (isOtp.isPresent() && (otp.equals(isOtp.get().getOtpCode().toString())) && password.equals(repassword)) {
                Account account = accountService.findByEmail(email);
                account.setPassword(passwordEncoder.encode(password));
                accountService.update(account);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("true", "Thay đổi mật khẩu thành công", "")
                );
            } else {
                return ResponseEntity.status(BAD_REQUEST).body(
                        new ResponseObject("false", "OTP không chính xác! Kiểm tra lại mã OTP trong gmail của bạn!", "")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("false", "Thay đổi mật khẩu thất bại", ""));
        }
    }
}
