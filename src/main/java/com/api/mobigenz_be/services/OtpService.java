package com.api.mobigenz_be.services;

import com.api.mobigenz_be.entities.Otp;
import java.util.Optional;

public interface OtpService {

    Otp save(Otp otp);

    Optional<Otp> findByEmail(String email);
}
