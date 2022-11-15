package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.DTOs.OptionDto;
import com.api.mobigenz_be.entities.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionsRepository extends JpaRepository<Option, Integer> {

    @Query("select o from Option o join ProductsOption po on o = po.option where po.product.id = :product_id")
    List<Option> getOptionsByProductId(@Param("product_id") Integer product_id);

    @Query("select o from Option o join OptionsValue ov on o = ov.optionId where ov.id = :option_id")
    Option getOptionByOptionValueId(@Param("option_id") Integer option_id);


    @Query("select o from Option o where o.optionName =:option_name")
    List<Option> getOptionByOptionName(@Param("option_name") String option_name );

}
