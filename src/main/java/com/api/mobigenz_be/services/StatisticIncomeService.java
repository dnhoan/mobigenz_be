package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.StatisticIncome;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StatisticIncomeService {
    List<StatisticIncome> getStatisticIncomeByYear(Integer year);

    List<Integer> statisticOrderStatus(Date s_date, Date e_date);
}
