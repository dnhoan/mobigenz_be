package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.*;
import com.api.mobigenz_be.entities.Account;
import com.api.mobigenz_be.entities.Customer;
import com.api.mobigenz_be.entities.ResponseObject;
import com.api.mobigenz_be.entities.Role;
import com.api.mobigenz_be.repositories.AccountRepository;
import com.api.mobigenz_be.repositories.CustomerRepository;
import com.api.mobigenz_be.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.api.mobigenz_be.services.CustomerService;

import static org.springframework.http.HttpStatus.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("customers/getAll")
    public ResponseEntity<ResponseDTO> getPageCustomers(
            @RequestParam(value = "offset", defaultValue = "") int offset,
            @RequestParam(value = "limit", defaultValue = "") int limit
    ) {
        PageDTO<CustomerDTO> items = this.customerService.getAll(offset, limit);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("customers", items))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }


    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    @PutMapping("customers/findByKey")
    public ResponseEntity<ResponseDTO> findByKey(
            @RequestParam(value = "offset", defaultValue = "") int offset
            , @RequestParam(value = "limit", defaultValue = "") int limit
            , @RequestBody SearchDTO searchDTO) {
        try {
            offset = offset < 0 ? 0 : offset;
            Pageable pageable;

            List<Sort.Order> orders = new ArrayList<>();
            List<ListSortDTO> listSortDTO = searchDTO.getListSortDTO();
            pageable = PageRequest.of(offset, limit, Sort.by("id"));
            Page<Customer> pageCustomer = this.customerService.findByKey(pageable, searchDTO.getValueSearch());
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("customers", pageCustomer))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("customers/getCustomerByAccountId")
    public ResponseEntity<ResponseDTO> getByAccountId(@RequestParam(value = "accountId") Integer accountId) {
        Customer customer = this.customerService.findByAccountId(accountId);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("customers", customer))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }


    @GetMapping("customers/customerName")
    public ResponseEntity<ResponseDTO> getByCustomerName(@RequestParam(value = "customerName") String customerName) {
        List<Customer> customer = this.customerService.findByCustomerName(customerName);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("customers", customer))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }


    @GetMapping("customers/email")
    public ResponseEntity<ResponseDTO> getByEmail(@RequestParam(value = "email") String email) {
        Customer customer = this.customerService.findByEmail(email);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("customers", customer))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }


    @PostMapping("customers")
    public ResponseEntity<ResponseObject> insertCustomer(@RequestBody Customer customer) {
        try {
            Customer cus = this.customerRepository.findCustomerByEmailorPhone(customer.getEmail(), customer.getPhoneNumber());
            if (cus != null) {
                if (cus.getPhoneNumber().equalsIgnoreCase(customer.getPhoneNumber())) {
                    return ResponseEntity.status(BAD_REQUEST).body(
                            new ResponseObject("false", "Số điện thoại đã tồn tại!", cus.getPhoneNumber())
                    );
                }
                if (cus.getEmail().equalsIgnoreCase(customer.getEmail())) {
                    return ResponseEntity.status(BAD_REQUEST).body(
                            new ResponseObject("false", "Email đã tồn tại!", cus.getEmail())
                    );
                }
            } else {
                CustomerDTO customerDTO = this.customerService.create(customer);
                return ResponseEntity.status(OK).body(
                        new ResponseObject("true", "Thêm khách hàng thành công!", customerDTO)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(BAD_REQUEST).body(
                new ResponseObject("false", "Thêm khách hàng thất bại!", "")
        );
    }

    @GetMapping("customers/{id}")
    public ResponseEntity<ResponseDTO> getCustomersById(@PathVariable("id") Integer id) {
        try {
            CustomerDTO customerDTO = this.customerService.getById(id);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("customer", customerDTO))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(NO_CONTENT)
                            .data(Map.of("message", "Customer not exist"))
                            .statusCode(NO_CONTENT.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        }
    }

    @PutMapping("customers")
    public ResponseEntity<ResponseObject> updateCustomer(@RequestBody Customer customer) {
        try {
            Customer cus = this.customerRepository.checkCustomer(customer.getId(), customer.getPhoneNumber());
            if (cus != null) {
                    return ResponseEntity.status(BAD_REQUEST).body(
                            new ResponseObject("false", "Số điện thoại đã tồn tại!", cus.getPhoneNumber())
                    );
                }else {
                Optional<Customer> customer1 = this.customerRepository.findById(customer.getId());
                if (customer1.isPresent())
                {
                    Account account = customer1.get().getAccount();
                    if (account != null) {
                        account.setCtime(LocalDateTime.now());
                        account.setPhoneNumber(customer.getPhoneNumber());
                        this.accountService.add(account);
                        customer.setBirthday(customer.getBirthday());
                        customer.setAccount(account);
                    }
                    this.customerService.update(customer);
                    List<CustomerDTO> customerDTOList = this.customerService.getAllCus();
                    return ResponseEntity.status(OK).body(
                            new ResponseObject("true", "Cập nhật khách hàng thành công!", customerDTOList)
                    );
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(BAD_REQUEST).body(
                new ResponseObject("false", "Cập nhật thất bại!", "")
        );
    }

    @DeleteMapping("customers/{id}")
    public ResponseEntity<ResponseObject> deleteCustomer(@PathVariable(value = "id") Integer id) {
        Customer customer = this.customerRepository.findCusById(id);
        if (customer != null) {
            this.customerService.delete(customer);
            List<CustomerDTO> customerDTOList = this.customerService.getAllCus();
            return ResponseEntity.status(OK).body(
                    new ResponseObject("true", "Xóa thành công!", customerDTOList)
            );

        }
        return ResponseEntity.status(OK).body(
                new ResponseObject("true", "Xóa thất bại!", "")
        );
    }

    @GetMapping("searchCustomer")
    public ResponseEntity<ResponseDTO> searchByAll(@RequestParam("search") String search) {
        List<CustomerDTO> customerDTOList = this.customerService.searchByAll(search);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("customer", customerDTOList))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

//    @GetMapping("searchCustomerByName")
//    public ResponseEntity<ResponseDTO> searchCustomerByName( @RequestParam("customerName") String customerName) {
//        List<CustomerDTO> customerDTOList = this.customerService.searchByName(customerName);
//        return ResponseEntity.ok(
//                ResponseDTO.builder()
//                        .status(OK)
//                        .data(Map.of("customer", customerDTOList))
//                        .statusCode(OK.value())
//                        .timeStamp(LocalDateTime.now())
//                        .build()
//        );
//    }
//
//    @GetMapping("searchCustomerByEmail")
//    public ResponseEntity<ResponseDTO> searchCustomerByEmail( @RequestParam("email") String email) {
//        List<CustomerDTO> customerDTOList = this.customerService.searchByEmail(email);
//        return ResponseEntity.ok(
//                ResponseDTO.builder()
//                        .status(OK)
//                        .data(Map.of("customer", customerDTOList))
//                        .statusCode(OK.value())
//                        .timeStamp(LocalDateTime.now())
//                        .build()
//        );
//    }
//
//    @GetMapping("searchCustomerByPhoneNumber")
//    public ResponseEntity<ResponseDTO> searchCustomerByPhoneNumber( @RequestParam("phoneNumber") String phoneNumber) {
//        List<CustomerDTO> customerDTOList = this.customerService.searchByPhoneNumber(phoneNumber);
//        return ResponseEntity.ok(
//                ResponseDTO.builder()
//                        .status(OK)
//                        .data(Map.of("customer", customerDTOList))
//                        .statusCode(OK.value())
//                        .timeStamp(LocalDateTime.now())
//                        .build()
//        );
//    }
//
//    @GetMapping("searchCustomerByAddress")
//    public ResponseEntity<ResponseDTO> searchCustomerByAddress( @RequestParam("address") String address) {
//        List<CustomerDTO> customerDTOList = this.customerService.searchByAddress(address);
//        return ResponseEntity.ok(
//                ResponseDTO.builder()
//                        .status(OK)
//                        .data(Map.of("customer", customerDTOList))
//                        .statusCode(OK.value())
//                        .timeStamp(LocalDateTime.now())
//                        .build()
//        );
//    }
}




