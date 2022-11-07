package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.OptionDto;
import com.api.mobigenz_be.DTOs.OptionValueDto;
import com.api.mobigenz_be.DTOs.ProductDto;
import com.api.mobigenz_be.entities.*;
import com.api.mobigenz_be.repositories.OptionsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptionsServiceImp implements OptionsService {

    @Autowired
    private OptionsRepository optionsRepository;
    @Autowired
    private OptionValueService optionValueService;

    @Autowired
    private ModelMapper modelMapper;

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

    @Transactional
    public OptionDto insertOption(OptionDto optionDto){
        Option option = this.optionsRepository.save(this.optionDtoMapToOption(optionDto));
        return this.optionMapToOptionDto(option);
    }


    private Option optionDtoMapToOption(OptionDto optionDto){

        Option option = Option
                .builder()
                .ctime(LocalDateTime.now())
                .optionName(optionDto.getOptionName())
                .optionsValues(new ArrayList<>())
                .build();

        return option;
    }
}
