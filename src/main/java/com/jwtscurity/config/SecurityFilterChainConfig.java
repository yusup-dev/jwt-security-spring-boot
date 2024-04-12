package com.jwtscurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityFilterChainConfig {

    private AuthenticationEntryPoint authenticationEntryPoint;

    private JWTAuthenticationFilter jwtAuthenticationFilter;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        {
            // disable CORS
            httpSecurity.cors(AbstractHttpConfigurer::disable);

            // Disable CSRF
            httpSecurity.csrf(AbstractHttpConfigurer::disable);

            //Http Request Filter
            httpSecurity.authorizeHttpRequests(
                    requestMatcher ->
                            requestMatcher.requestMatchers("/api/auth/login/**").permitAll()
                                    .requestMatchers("/api/auth/sign-up/**").permitAll()
                                    .anyRequest().authenticated()

            );

            // Authentication Entry Point -> Exception Handler
            httpSecurity.exceptionHandling(
                    exceptionConfig -> exceptionConfig.authenticationEntryPoint(authenticationEntryPoint)
            );

            // Set session policy = STATELESS
            httpSecurity.sessionManagement(
                    sessionConfig-> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

            // Add JWT Authentication Filter
            httpSecurity.addFilterBefore(jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
                    );
        }
        return null;
    }

}
