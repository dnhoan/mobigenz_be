package com.api.mobigenz_be.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO <T>{
    int totalPages;
    long totalElement;
    long offset;
    long limit;
    List<T> items;
    boolean isFirst;
    boolean isLast;
    boolean hasNext;
    boolean hasPrevious;
}
