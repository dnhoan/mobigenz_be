package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.OptionDto;
import com.api.mobigenz_be.DTOs.OptionValueDto;
import com.api.mobigenz_be.entities.Option;
import com.api.mobigenz_be.entities.OptionsValue;
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
    public List<OptionDto> getList() {
        return this.optionsRepository.findAll()
                .stream()
                .map(this::optionMapToOptionDto)
                .collect(Collectors.toList());
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

    private OptionDto optionMapToOptionDto(Option option) {
        List<OptionValueDto> optionValueDtos = option.getOptionsValues()
                .stream()
                .map(this::optionValueMapToOptionValueDto)
                .collect(Collectors.toList());
        return OptionDto
                .builder()
                .id(option.getId())
                .optionName(option.getOptionName())
                .mtime(option.getMtime())
                .ctime(option.getCtime())
                .status(option.getStatus())
                .optionValueDtos(optionValueDtos)
                .build();
    }

    private OptionValueDto optionValueMapToOptionValueDto(OptionsValue optionsValue) {
        return OptionValueDto
                .builder()
                .id(optionsValue.getId())
                .optionValueName(optionsValue.getOptionValueName())
                .optionName(optionsValue.getOptionName())
                .status(optionsValue.getStatus())
                .build();
    }
}
