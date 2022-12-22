package com.api.mobigenz_be.DTOs;

import com.api.mobigenz_be.entities.ProductDetail;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {

    private Integer id;

    private List<ImeiDto> imeiDtoList;

    private ProductDetailCartDto productDetailCartDto;

    private Double priceSell;

    private Double productPrice;

    private Integer amount;

    private LocalDateTime ctime;

    private LocalDateTime mtime;

    private String productName;

    private String note;

    private boolean expand;
}
