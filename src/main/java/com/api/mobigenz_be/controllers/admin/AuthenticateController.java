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

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = Constant.Api.Path.PREFIX)
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticateController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

    public AuthenticateController(AuthenticationManagerBuilder authenticationManagerBuilder,TokenProvider tokenProvider) {
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



//    @GetMapping("mobilegenz/login")
//    public ResponseEntity<ResponseObject> login(@RequestParam String email, @RequestParam String password) {
//        try {
//            Account account = this.accountRepository.findAccountByEmail(email);
//            Boolean check = EncryptUtils.check(password, account.getPassword());
//            if (check) {
//                return ResponseEntity.status(HttpStatus.OK).body(
//                        new ResponseObject("true", "Đăng nhập thành công!", account)
//                );
//            }
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    new ResponseObject("false", "Đăng nhập not thành công!", null)
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    new ResponseObject("false", "Đăng nhập thất bại!", e)
//            );
//        }
//    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateAdmin(@Valid @RequestBody LoginVM loginVM) {
//		Tạo chuỗi authentication từ email và password (object LoginRequest
        try {
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
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

//        User userLogin = userService.findUserByUserName(adminLoginVM.getUserName());
//        return ResponseEntity.ok().body(new JWTTokenResponse(jwt, userLogin.getUserName())); //Trả về chuỗi jwt(authentication string)

    }

    @PostMapping("register")
    public ResponseEntity<ResponseDTO> insert(@RequestBody Account account) {
        try {
            String password = passwordEncoder.encode(account.getPassword());
            String email = account.getEmail();
            String name = account.getEmail().substring(0,email.indexOf("@"));
            Set<Role> roles = new HashSet<>();
            Role role = this.roleServiceImpl.getRoleById(1);
            roles.add(role);
            account.setRoles(roles);
            account.setPassword(password);
            account.setStatus(0);

            Customer customer = new Customer();
            customer.setCustomerName(name);
            customer.setAccount(account);
            customer.setEmail(account.getEmail());
            customer.setBirthday(LocalDate.of(1990,1,1));
            customer.setCtime(LocalDate.now());
            customer.setPhoneNumber(account.getPhoneNumber());
            account.setCustomer(customer);
//          CustomerDTO customerDTO = this.customerService.create(customer);
            AccountDTO accountDTO = this.accountService.add(account);

            System.out.println(customer);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("account", accountDTO))
//                           .data(Map.of("customer",customerDTO))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("forgot")
    public ResponseEntity<ResponseObject> forgot(@RequestParam(name = "email") String email, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            Account account = this.accountRepository.findAccountByEmail(email);
            System.out.println(account.getEmail());
            if (account.getEmail().equalsIgnoreCase(email)) {
                Random random = new Random();
                int otp = random.nextInt(900000) + 100000;
                emailSenderService.sendSimpleEmail(email, "OTP code", "Your OTP code is: " + otp);
                session.setAttribute(email, otp);
                System.out.println(session);
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("true", "OTP da duoc gui den email cua ban!","")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("false", "Email ko ton tai!", email)
            );
        }
    }

    @GetMapping("getOTP")
    public ResponseEntity<ResponseObject> getOtp(@RequestParam(name = "email") String email, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object isOtp = session.getAttribute(email);
        System.out.println(isOtp);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("true", "OTP la: ", isOtp)
        );
    }
}
