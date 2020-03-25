package com.umssonline.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeRequests().antMatchers("/users/**").permitAll();
//        http.authorizeRequests().antMatchers("/**").hasIpAddress(environment.getProperty("uo.auth.gateway.ip")); // When we want to specify the source address
        http.headers().frameOptions().disable();
    }
}
