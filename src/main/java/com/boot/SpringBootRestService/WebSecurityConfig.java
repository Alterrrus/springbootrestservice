package com.boot.SpringBootRestService;

import com.boot.SpringBootRestService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    UserService userService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
    @Configuration
    @Order(1)
    public static class WebSecurityConfigAdmin extends WebSecurityConfigurerAdapter {
        @Autowired
        private AuthenticationEntryPointImpl entryPoint;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.antMatcher("/rest/admin/**")
                    .authorizeRequests().anyRequest().hasRole("ADMIN")
                    .and().httpBasic().authenticationEntryPoint(entryPoint);
        }
    }
    @Configuration
    @Order(2)
    public static class WebSecurityConfigUser extends WebSecurityConfigurerAdapter {
        @Autowired
        private AuthenticationEntryPointImplUser entryPoint;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.antMatcher("/rest/user/**")
                    .authorizeRequests().anyRequest().hasRole("USER")
                    .and().httpBasic().authenticationEntryPoint(entryPoint);
        }
    }
    @Configuration
    @Order(3)
    public static class WebSecurityConfigAnonim extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/rest/profile/register/** ").authorizeRequests().anyRequest().permitAll();
        }
    }

}
