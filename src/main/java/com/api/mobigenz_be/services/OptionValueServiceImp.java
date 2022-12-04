package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.OptionDto;
import com.api.mobigenz_be.DTOs.OptionValueDto;
import com.api.mobigenz_be.entities.Option;
import com.api.mobigenz_be.entities.OptionsValue;
import com.api.mobigenz_be.repositories.OptionValueRepository;
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
public class OptionValueServiceImp implements OptionValueService{

    @Autowired
    private OptionValueRepository optionValueRepository;

    @Autowired
    private OptionsRepository optionsRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<OptionValueDto> getList() {
        return this.optionValueRepository.findAll()
                .stream()
                .map(this::optionValueMapToOptionValueDto)
                .collect(Collectors.toList());

    }


    @Override
    public List<OptionValueDto> getOptionsValueByProductIdAndOptionId(Integer product_id, Integer option_id) {
        List<OptionsValue> optionValueDtos = this.optionValueRepository.getOptionsValueByProductIdAndOptionId(product_id, option_id);
        return optionValueDtos.stream().map(this::optionValueMapToOptionValueDto).collect(Collectors.toList());
    }

    @Override
    public List<Option> getOptionByOptionName(String option_name) {
        List<Option> options = this.optionsRepository.getOptionByOptionName(option_name);

        return options;

        //return options.stream().map(this::optionMapToOptionDto).collect(Collectors.toList());
    }

    private OptionDto optionMapToOptionDto(Option option){
        return OptionDto.builder()
                .id(option.getId())
                .optionName(option.getOptionName())
                .status(option.getStatus())
                .note(option.getNote())
                .ctime(option.getCtime())
                .build();

    }



    private OptionValueDto optionValueMapToOptionValueDto(OptionsValue optionValue) {
        Option option = this.optionsRepository.getOptionByOptionValueId(optionValue.getId());
        return OptionValueDto.builder()
                .id(optionValue.getId())
                .optionValueName(optionValue.getOptionValueName())
                .optionName(optionValue.getOptionName())
                .optionId(this.optionMapToOptionDto(option))
                .ctime(optionValue.getCtime())
                .mtime(optionValue.getMtime())
                .note(optionValue.getNote())
                .status(optionValue.getStatus())
                .build();
    }

    @Transactional
    public OptionValueDto saveOptionValue(Integer option_id, String optionValueName){
        OptionsValue optionsValue = OptionsValue.builder().optionValueName(optionValueName).build();
        optionsValue.setOptionId(Option.builder().id(option_id).build());
        optionsValue.setOptionName("");
        optionsValue.setCtime(LocalDateTime.now());
        optionsValue = this.optionValueRepository.save(optionsValue);
        return this.modelMapper.map(optionsValue, OptionValueDto.class);

    }


    private OptionsValue optionValueDtoMapToOptionValue(OptionValueDto optionValueDto){
      //  ProductsVariant productsVariant = modelMapper.map(optionValueDto.getProductVariantsId(), ProductsVariant.class);
       Option option = modelMapper.map(optionValueDto.getOptionId(), Option.class);
        OptionsValue optionsValue = OptionsValue
                .builder()
                .ctime(LocalDateTime.now())
                .status(1)
                .optionValueName(optionValueDto.getOptionValueName())
                .optionName(optionValueDto.getOptionName())
                .optionId(option)
                .build();


        return optionsValue;
    }



}
