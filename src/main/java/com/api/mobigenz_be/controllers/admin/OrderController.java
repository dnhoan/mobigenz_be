package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.OrderDto;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.constants.UrlConstant;
import com.api.mobigenz_be.models.StatusUpdate;
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
@RequestMapping("api/admin")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("order")
    public ResponseEntity<ResponseDTO> saveOrder(@RequestBody OrderDto orderDto) {
        orderDto = this.orderService.adminSaveOrder(orderDto);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("order", orderDto))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("updateOrderStatus")
    public ResponseEntity<ResponseDTO> cancelOrder(
            @RequestBody StatusUpdate statusUpdate) {
        this.orderService.updateOrderStatus(statusUpdate.getOrderId(), statusUpdate.getNewStatus(), statusUpdate.getNote());
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("result", true))
                        .status(OK)
                        .statusCode(OK.value())
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

    @GetMapping("orders")
    public ResponseEntity<ResponseDTO> getOrdersByOrderStatus(@RequestParam(name = "orderStatus",defaultValue = "", required = false) Integer orderStatus) {
        List<OrderDto> orderDtos = this.orderService.getOrdersByOrderStatus(orderStatus);
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

