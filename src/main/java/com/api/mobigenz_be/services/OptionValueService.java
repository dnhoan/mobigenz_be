package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.OptionValueDto;

import java.util.List;

public interface OptionValueService {
    List<OptionValueDto> getOptionsValueByProductIdAndOptionId(Integer product_id, Integer option_id);
}
