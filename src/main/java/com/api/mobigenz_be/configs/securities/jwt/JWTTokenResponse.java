package com.api.mobigenz_be.configs.securities.jwt;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JWTTokenResponse {
    String jwt;
    String username;
}
