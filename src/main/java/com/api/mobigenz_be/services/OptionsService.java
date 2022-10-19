package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.OptionDto;
import com.api.mobigenz_be.entities.Option;

import java.util.List;

public interface OptionsService {
    public List<Option> getList();
    List<OptionDto> getOptionsByProductId(Integer product_id);
}
