package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.ImeiDto;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.constants.UrlConstant;
import com.api.mobigenz_be.services.ImeiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(UrlConstant.baseUrlFE)
@RequestMapping("api/admin")
public class ImeiController {

    @Autowired
    private ImeiService imeiService;

    @GetMapping("imeis")
    public ResponseEntity<ResponseDTO> getImeisByProductDetailId(@RequestParam("product_detail_id") Integer productDetailId) {
        List<ImeiDto> imeiDtos = this.imeiService.getImeisByProductDetailId(productDetailId);
        return ResponseEntity.ok(ResponseDTO
                .builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .data(Map.of("imeis", imeiDtos))
                .build());
    }

    @PostMapping("imei")
    public ResponseEntity<ResponseDTO> save(@RequestBody ImeiDto imeiDto) {
        ImeiDto resImeiDto = this.imeiService.save(imeiDto);
        return ResponseEntity.ok(ResponseDTO
                .builder()
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .timeStamp(LocalDateTime.now())
                .data(Map.of("imei", resImeiDto))
                .build());
    }

    @DeleteMapping("imei/{id}")
    public ResponseEntity<ResponseDTO> deleteImei(@PathVariable("id") Integer id) {
        boolean res = this.imeiService.removeImei(id);
        return ResponseEntity.ok(ResponseDTO
                .builder()
                .status(res ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR)
                .statusCode(res ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timeStamp(LocalDateTime.now())
                .data(Map.of("result", res))
                .build());
    }
}
