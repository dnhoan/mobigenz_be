package com.api.mobigenz_be.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Builder
//@Setter
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class StatisticIncome {
//    private long dt_online;
//    private long dt_store;
//    private Integer year;
//    private Integer month;
//    private Integer day;
//    private Integer days_of_week;
//
//    public StatisticIncome(long dt_online, long dt_store, Integer month) {
//        this.dt_online = dt_online;
//        this.dt_store = dt_store;
//        this.month = month;
//    }
//
//
//}

public interface StatisticIncome{
    Long getDt_online();

    Long getDt_store();

    Integer getThang();

}