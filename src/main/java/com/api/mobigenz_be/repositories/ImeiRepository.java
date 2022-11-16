package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.entities.Imei;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImeiRepository extends JpaRepository<Imei, Integer> {

//    @Query("select i from Imei i where i.productDetail.id = :productDetailId")
    List<Imei> getImeiByProductDetailId(Integer productDetailId);

}
