package com.api.mobigenz_be.controllers.user;

import com.api.mobigenz_be.DTOs.OrderDto;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.DTOs.UserOrderDto;
import com.api.mobigenz_be.constants.UrlConstant;
import com.api.mobigenz_be.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin(UrlConstant.baseUrlFE)
@RequestMapping("api/user")
public class OrderShopController {

    @Autowired
    private OrderService orderService;

    @PostMapping("order")
    public ResponseEntity<ResponseDTO> createOrder(@RequestBody OrderDto orderDto) {
        boolean result = this.orderService.saveOrder(orderDto);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("result", result))
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );

    }

    @GetMapping("order/{order_id}")
    public ResponseEntity<ResponseDTO> getOrderById(@PathVariable("order_id") Integer order_id) {
        OrderDto orderDto = this.orderService.getOrderById(order_id);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("order", orderDto))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );

    }

    @GetMapping("customerOrders/{customer_id}")
    public ResponseEntity<ResponseDTO> getOrdersByCustomerId(@PathVariable("customer_id") Integer customer_id) {
        List<OrderDto> orderDtos = this.orderService.getOrdersByCustomerId(customer_id);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("orders", orderDtos))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }
}