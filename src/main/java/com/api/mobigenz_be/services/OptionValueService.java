package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.OptionValueDto;
import com.api.mobigenz_be.entities.Option;
import com.api.mobigenz_be.entities.OptionsValue;

import java.util.List;

public interface OptionValueService {


    List<OptionValueDto> getOptionsValueByProductIdAndOptionId(Integer product_id, Integer option_id);

    List<OptionValueDto> getList();

    OptionsValue saveOptionValue(OptionsValue optionsValue);

    List<Option> getOptionByOptionName(String option_Name);
}
