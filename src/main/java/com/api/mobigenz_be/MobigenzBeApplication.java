package com.api.mobigenz_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableSwagger2
public class MobigenzBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobigenzBeApplication.class, args);
    }

}
