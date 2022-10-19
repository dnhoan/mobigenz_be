package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.OptionDto;
import com.api.mobigenz_be.DTOs.OptionValueDto;
import com.api.mobigenz_be.entities.Option;
import com.api.mobigenz_be.repositories.OptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptionsServiceImp implements OptionsService {

    @Autowired
    private OptionsRepository optionsRepository;
    @Autowired
    private OptionValueService optionValueService;

    @Override
    public List<Option> getList() {
        return this.optionsRepository.findAll();
    }

    @Override
    public List<OptionDto> getOptionsByProductId(Integer product_id) {
        List<Option> options = this.optionsRepository.getOptionsByProductId(product_id);
        return options.stream().map(option -> {
                    List<OptionValueDto> optionValueDtos = this.optionValueService.getOptionsValueByProductIdAndOptionId(product_id, option.getId());
                    return OptionDto.builder()
                            .id(option.getId())
                            .optionName(option.getOptionName())
                            .note(option.getNote())
                            .ctime(option.getCtime())
                            .mtime(option.getMtime())
                            .status(option.getStatus())
                            .optionValueDtos(optionValueDtos)
                            .build();
                }
        ).collect(Collectors.toList());
    }
}
