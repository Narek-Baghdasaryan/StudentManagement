package com.example.studentmanagement.config;

import com.example.studentmanagement.enums.UserType;
import com.example.studentmanagement.security.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final PasswordEncoder passwordEncoder;


    private final UserDetailService userDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/loginPage").permitAll()
                .requestMatchers("/teachers/add").permitAll()
                .requestMatchers("/students/add").permitAll()
                .requestMatchers("/lessons/add").hasAuthority(UserType.TEACHER.name())
                .requestMatchers("/lessons/delete").hasAuthority(UserType.TEACHER.name())
                .requestMatchers("/lessons/update").hasAuthority(UserType.TEACHER.name())
                .requestMatchers("/students/delete").hasAuthority(UserType.TEACHER.name())
                .requestMatchers("/students/update").hasAuthority(UserType.TEACHER.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/login")
                .and()
                .logout()
                .logoutSuccessUrl("/");
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
}