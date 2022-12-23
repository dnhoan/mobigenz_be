package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.StatisticIncome;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StatisticIncomeService {
    List<StatisticIncome> getStatisticIncomeByYear(Integer year);

    List<Integer> statisticOrderStatus(Date s_date, Date e_date);

    List<StatisticIncome> getStatisticIncomeByDate(int s_day, int e_day, int s_month, int e_month, int s_year, int e_year);
}
