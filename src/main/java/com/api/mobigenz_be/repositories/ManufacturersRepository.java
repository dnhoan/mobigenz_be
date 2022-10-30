package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.DTOs.ManufacturerDto;
import com.api.mobigenz_be.DTOs.ProductDto;
import com.api.mobigenz_be.DTOs.ProductLineDto;
import com.api.mobigenz_be.entities.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturersRepository extends JpaRepository<Manufacturer, Integer> {

    @Query("select m " +
            "from ProductLine pl join Manufacturer m on pl.manufacturer = m where pl.id = :product_line_id")
    Manufacturer getManufacturerByProductLineId(@Param("product_line_id") Integer product_line_id);
}
