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
        List<OptionValueDto> optionValueDtos = option.getOptionsValues()
                .stream()
                .map(this::optionValueMapToOptionValueDto)
                .collect(Collectors.toList());
        return OptionDto
                .builder()
                .id(option.getId())
                .optionName(option.getOptionName())
                .ctime(option.getCtime())
                .status(option.getStatus())
                .build();
    }



    private OptionValueDto optionValueMapToOptionValueDto(OptionsValue optionValue) {
       // OptionDto optionDto = this.optionsRepository.getOptionByOptionValueId(optionValue.getId());
        return OptionValueDto.builder()
                .id(optionValue.getId())
                .optionValueName(optionValue.getOptionValueName())
                .optionName(optionValue.getOptionName())
               // .optionDto(optionDto)
                .ctime(optionValue.getCtime())
                .mtime(optionValue.getMtime())
                .note(optionValue.getNote())
                .status(optionValue.getStatus())
                .build();
    }

    @Transactional
    public OptionsValue saveOptionValue(OptionsValue optionsValue){
//        OptionsValue optionsValue = this.optionValueRepository.save(this.optionValueDtoMapToOptionValue(optionValueDto));
//        return this.optionValueMapToOptionValueDto(optionsValue);
//
//        System.out.println("123455");;
//
//        System.out.println(optionsValue.getOptionName());
//
//
//        optionsValue.setOptionId((Option) this.getOptionByOptionName(optionsValue.getOptionName()));

        optionsValue.setCtime(LocalDateTime.now());
        optionsValue.setProductsVariants(new ArrayList<>());
        System.out.println("fdvdv");
        System.out.println(optionsValue.getOptionId());
        return optionValueRepository.save(optionsValue);

    }


    private OptionsValue optionValueDtoMapToOptionValue(OptionValueDto optionValueDto){
      //  ProductsVariant productsVariant = modelMapper.map(optionValueDto.getProductVariantsId(), ProductsVariant.class);
//       Option option = modelMapper.map(optionValueDto.getOptionDto(), Option.class);
        OptionsValue optionsValue = OptionsValue
                .builder()
                .ctime(LocalDateTime.now())
                .status(1)
                .id(optionValueDto.getId())
                .optionValueName(optionValueDto.getOptionValueName())
                .optionName(optionValueDto.getOptionName())
              //  .option(option)
                .build();
       // optionsValue.setOptionId((Option) getOptionByOptionName(optionValueDto.getOptionName()));



        return optionsValue;
    }



}
