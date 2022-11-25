package com.api.mobigenz_be.models;

import lombok.Data;

@Data
public class StatusUpdate {
    private Integer orderId;
    private String note;
    private Integer newStatus;
}
