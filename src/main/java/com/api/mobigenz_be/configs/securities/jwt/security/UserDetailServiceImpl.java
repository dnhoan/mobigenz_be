package com.api.mobigenz_be.configs.securities.jwt.security;


import com.api.mobigenz_be.entities.Account;
import com.api.mobigenz_be.entities.Role;
import com.api.mobigenz_be.repositories.AccountRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    public final AccountRepository accountRepositoryJpa;

    public UserDetailServiceImpl(AccountRepository accountRepositoryJpa) {
        this.accountRepositoryJpa = accountRepositoryJpa;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Account account = accountRepositoryJpa.findAccountByEmail(email);

        if (account == null) {
            throw new UsernameNotFoundException("Email not found!");
        }

        List<GrantedAuthority> grantedAuthorities
                = account.getRoles().stream().map(authority -> new SimpleGrantedAuthority(authority.getRoleName())).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(account.getEmail(), account.getPassword(), grantedAuthorities);

    }

//    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String userName, User user) {
//        if (!user.isActive()) {
//            throw new UserNotActivatedException("User " + userName + " was not activated");
//        }
//         List<GrantedAuthority> grantedAuthorities
//                 = user.getRoles().stream().map(authority -> new SimpleGrantedAuthority(authority.getCode())).collect(Collectors.toList());
//        // có thể thay thế bằng đoạn code bên dưới cho dễ hiểu
////        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
////        Set<Role> roles = user.getRoles();
////        for (Role role : roles) {
////            grantedAuthorities.add(new SimpleGrantedAuthority(role.getCode()));
////        }
//        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), grantedAuthorities);
//    }
}

