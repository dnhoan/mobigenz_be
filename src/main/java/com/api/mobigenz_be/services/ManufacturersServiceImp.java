package com.api.mobigenz_be.services;

import com.api.mobigenz_be.entities.Manufacturer;
import com.api.mobigenz_be.repositories.ManufacturersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ManufacturersServiceImp implements ManufacturersService {

    @Autowired
    private ManufacturersRepository manufacturersRepository;

    @Override
    public List<Manufacturer> getList() {
        return this.manufacturersRepository.findAll();
    }
}
