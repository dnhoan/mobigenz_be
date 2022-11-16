package com.api.mobigenz_be.configs.securities.jwt;


import com.api.mobigenz_be.DTOs.CustomerDTO;
import com.api.mobigenz_be.controllers.admin.vm.LoginVM;
import com.api.mobigenz_be.entities.Account;
import com.api.mobigenz_be.entities.Customer;
import com.api.mobigenz_be.services.AccountService;
import com.api.mobigenz_be.services.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class TokenProvider implements InitializingBean {
    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private Key key;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${application.security.jwt.secretKey}")
    private String secretKey;

    @Value("${application.security.jwt.tokenValidityMilliseconds}")
    private long tokenValidityInMilliseconds;

    @Value("${application.security.jwt.rememberMeTokenValiditySeconds}")
    private long tokenValidityInSecondsForRememberMe;


    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = secretKey.getBytes();
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication, boolean rememberMe){
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        long now = Calendar.getInstance().getTimeInMillis();
        Date expiredTime = new Date((rememberMe ? tokenValidityInSecondsForRememberMe : tokenValidityInMilliseconds) + now);
        return Jwts.builder()
                .setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities).signWith(this.key, SignatureAlgorithm.HS256)
                .setExpiration(expiredTime).compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.key).parseClaimsJws(token).getBody();

        Collection<GrantedAuthority> authorities = Arrays.asList(claims.get(AUTHORITIES_KEY).toString().split(",")).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature.", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token.", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token.", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid.", e);
        }
        return false;
    }

}

