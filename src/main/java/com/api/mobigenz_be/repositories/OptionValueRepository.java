package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.entities.OptionsValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionValueRepository extends JpaRepository<OptionsValue, Integer> {

    @Query("select o from OptionsValue o join ProductsVariant pv on pv.optionValue = o join ProductsOption po on pv.productOption = po  where po.product.id = :product_id and po.option.id = :option_id")
    List<OptionsValue> getOptionsValueByProductIdAndOptionId(@Param("product_id") Integer product_id, @Param("option_id") Integer option_id);
}
