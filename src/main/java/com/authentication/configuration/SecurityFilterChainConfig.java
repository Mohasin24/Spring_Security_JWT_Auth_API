package com.authentication.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityFilterChainConfig {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    public SecurityFilterChainConfig(AuthenticationEntryPoint authenticationEntryPoint,JWTAuthenticationFilter jwtAuthenticationFilter){
        this.authenticationEntryPoint=authenticationEntryPoint;
        this.jwtAuthenticationFilter=jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // Disable CORS
        httpSecurity.cors(cors->cors.disable());

        // Disable CSRF
        httpSecurity.csrf(csrf->csrf.disable());

        httpSecurity.authorizeHttpRequests(
                auth ->
                        auth.requestMatchers("api/auth/login/**").permitAll()
                                .requestMatchers("api/auth/sign-up/**").permitAll()
                                .anyRequest().authenticated()
        );

        // Authentication Entry point -> Exception Handler
        httpSecurity.exceptionHandling(
                exceptionConfig->exceptionConfig.authenticationEntryPoint(authenticationEntryPoint)
        );

        // Set session policy stateless
        httpSecurity.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // add authentication filter
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // final build
        return httpSecurity.build();
    }
}
