package com.api.mobigenz_be.controllers.admin;


import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.DTOs.SpecificationDto;
import com.api.mobigenz_be.DTOs.StatisticIncome;
import com.api.mobigenz_be.constants.Constant;
import com.api.mobigenz_be.constants.UrlConstant;
import com.api.mobigenz_be.services.StatisticIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(Constant.Api.Path.ADMIN)
@CrossOrigin(UrlConstant.baseUrlFE)
public class StatisticIncomeController {

    @Autowired
    private StatisticIncomeService statisticIncomeService;

    @GetMapping("statisticIncome/year/{year}")
    public ResponseEntity<ResponseDTO> getStatisticIncomeByYear(
            @PathVariable("year") Integer year
    ) {
        List<StatisticIncome> statisticIncomes = this.statisticIncomeService.getStatisticIncomeByYear(year);
        return ResponseEntity.ok(
                ResponseDTO
                        .builder()
                        .data(Map.of("statisticIncomes", statisticIncomes))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );

    }

    @GetMapping("statisticOrderStatus")
    public ResponseEntity<ResponseDTO> statisticOrderStatus(
            @RequestParam("s_date") String s_date,
            @RequestParam("e_date") String e_date
    ) {
        try {
            Date sDate = new SimpleDateFormat("dd/MM/yyyy").parse(s_date);
            Date eDate = new SimpleDateFormat("dd/MM/yyyy").parse(e_date);
            List<Integer> statisticOrderStatus = this.statisticIncomeService.statisticOrderStatus(sDate, eDate);
            return ResponseEntity.ok(
                    ResponseDTO
                            .builder()
                            .data(Map.of("statisticOrderStatus", statisticOrderStatus))
                            .status(OK)
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(
                    ResponseDTO
                            .builder()
                            .status(EXPECTATION_FAILED)
                            .statusCode(EXPECTATION_FAILED.value())
                            .timeStamp(LocalDateTime.now())
                            .build());
        }
    }
}
