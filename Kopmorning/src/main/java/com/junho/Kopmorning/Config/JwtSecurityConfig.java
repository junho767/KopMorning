package com.junho.Kopmorning.Config;

import com.junho.Kopmorning.jwt.JwtFilter;
import com.junho.Kopmorning.jwt.TokenProvider;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private static TokenProvider tokenProvider;

    @Override
    public void configure(HttpSecurity http){
        JwtFilter customFilter = new JwtFilter(tokenProvider);
        http.addFilterBefore((Filter) customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
