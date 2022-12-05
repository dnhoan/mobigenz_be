package com.api.mobigenz_be.services;

import com.api.mobigenz_be.entities.Otp;
import com.api.mobigenz_be.repositories.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Override
    public Otp save(Otp otp) {
        return this.otpRepository.save(otp);
    }

    @Override
    public Optional<Otp> findByEmail(String email) {
        return this.otpRepository.findByEmail(email);
    }
}
