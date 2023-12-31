package com.dcorp.hightech.api.users.photoappapiusers.security;

import com.dcorp.hightech.api.users.photoappapiusers.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Slf4j
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final Environment environment;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("Ip Gateway {}", environment.getProperty("gateway.ip"));
        // Configure Authentication Manager Builder
        AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
        sharedObject
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authenticationManager = sharedObject.build();
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager, userService, environment);
        authenticationFilter.setFilterProcessesUrl("/users/login");

        http.csrf(AbstractHttpConfigurer::disable);
        http
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/users/**").access(new WebExpressionAuthorizationManager("hasIpAddress('"+ environment.getProperty("gateway.ip") +"')"))
                                .requestMatchers(HttpMethod.GET, "/users/status/check").permitAll()
                                .requestMatchers(HttpMethod.GET, "/actuator/**").access(new WebExpressionAuthorizationManager("hasIpAddress('"+ environment.getProperty("gateway.ip")+"')"))
                ).addFilter(authenticationFilter)
                .authenticationManager(authenticationManager);

        return http.build();
    }

}
