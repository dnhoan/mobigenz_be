package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.ImeiDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ImeiService {
    List<ImeiDto> getImeisByProductDetailId(Integer productDetailId);

    ImeiDto save(ImeiDto imeiDto);

    boolean removeImei(Integer id);
}
