package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.StatisticIncome;
import com.api.mobigenz_be.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class StatisticIncomeServiceImp implements StatisticIncomeService{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<StatisticIncome> getStatisticIncomeByYear(Integer year) {
        return this.orderRepository.getStatisticIncomeByYear(year);
    }

    @Override
    public List<Integer> statisticOrderStatus(Date s_date, Date e_date) {
        return this.orderRepository.statisticOrderStatus(s_date, e_date);
    }


}
