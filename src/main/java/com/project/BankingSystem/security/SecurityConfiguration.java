package com.project.BankingSystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
    @Autowired
    private CustomUserDetails userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.csrf().disable().authorizeRequests()
                .mvcMatchers("/get/**", "/transfer/{id}/{targetId}", "/access-account/{secretKey}").hasRole("USER")
                .mvcMatchers("/get/**", "/credit/account/{id}", "/debit/account/{id}", "/create/**",
                        "/account-holder/{id}", "/show-accounts", "/get/checking-balance",
                        "/access-account/admin/{id}").hasRole("ADMIN")
                .mvcMatchers("/creditTP/account/{hashedKey}", "/debitTP/account/{hashedKey}").hasRole("THIRD_PARTY")
                .anyRequest().permitAll();
        http.csrf().disable();
        return http.build();

    }
}