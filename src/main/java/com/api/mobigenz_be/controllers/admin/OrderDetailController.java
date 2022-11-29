package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.OrderDetailDto;
import com.api.mobigenz_be.DTOs.OrderDto;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.constants.UrlConstant;
import com.api.mobigenz_be.services.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin(UrlConstant.baseUrlFE)
@RequestMapping("api/admin")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @DeleteMapping("deleteOrderDetail/{id}")
    public ResponseEntity<ResponseDTO> deleteOrderDetail(@PathVariable("id") Integer id) {
        boolean result = this.orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("res", result))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @PostMapping("addOrderDetailToOrder/{order_id}")
    public ResponseEntity<ResponseDTO> addOrderDetailToOrder(
            @PathVariable("order_id") Integer order_id,
            @RequestBody OrderDetailDto orderDetailDto
    ) {
        orderDetailDto = this.orderDetailService.addOrderDetailToOrder(order_id, orderDetailDto);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("orderDetail", orderDetailDto))
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @PostMapping("createOrderDetailWhenExchangeImei/{orderId}/{oldImeiId}")
    public ResponseEntity<ResponseDTO> createOrderDetailWhenExchangeImei(
            @PathVariable("orderId") Integer orderId,
            @PathVariable("oldImeiId") Integer oldImeiId,
            @RequestBody OrderDetailDto orderDetailDto
    ) {
        orderDetailDto = this.orderDetailService.createOrderDetailWhenExchangeImei(oldImeiId,orderId, orderDetailDto);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("orderDetail", orderDetailDto))
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @PostMapping("changeOrderDetail/{orderId}/{currentOrderDetailId}")
    public ResponseEntity<ResponseDTO> changeOrderDetail(
            @PathVariable("orderId") Integer orderId,
            @PathVariable("currentOrderDetailId") Integer currentOrderDetailId,
            @RequestBody OrderDetailDto orderDetailDto
    ) {
        orderDetailDto = this.orderDetailService.changeOrderDetail(currentOrderDetailId,orderId, orderDetailDto);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("orderDetail", orderDetailDto))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

}
