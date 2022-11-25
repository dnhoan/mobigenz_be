package com.api.mobigenz_be.DTOs;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListSortDTO {
    private String customerName;
    private String email;
    private String phoneNumber;
    private Integer status;
}
