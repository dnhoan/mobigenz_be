package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.OptionValueDto;
import com.api.mobigenz_be.entities.OptionsValue;
import com.api.mobigenz_be.repositories.OptionValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptionValueServiceImp implements OptionValueService{

    @Autowired
    private OptionValueRepository optionValueRepository;

    @Override
    public List<OptionValueDto> getOptionsValueByProductIdAndOptionId(Integer product_id, Integer option_id) {
        List<OptionsValue> optionValueDtos = this.optionValueRepository.getOptionsValueByProductIdAndOptionId(product_id, option_id);
        return optionValueDtos.stream().map(this::optionValueMapToOptionValueDto).collect(Collectors.toList());
    }

    private OptionValueDto optionValueMapToOptionValueDto(OptionsValue optionValue) {
        return OptionValueDto.builder()
                .id(optionValue.getId())
                .optionValueName(optionValue.getOptionValueName())
                .optionName(optionValue.getOptionName())
                .ctime(optionValue.getCtime())
                .mtime(optionValue.getMtime())
                .note(optionValue.getNote())
                .status(optionValue.getStatus())
                .build();
    }
}
