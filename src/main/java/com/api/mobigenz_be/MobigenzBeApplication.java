package com.api.mobigenz_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class MobigenzBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobigenzBeApplication.class, args);
    }

}
