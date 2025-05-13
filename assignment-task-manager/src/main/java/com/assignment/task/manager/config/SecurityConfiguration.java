package com.assignment.task.manager.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Objects;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${gateway.secret}")
    private String gatewaySecret;

    @Value("${gateway.header}")
    private String gatewayHeader;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(new GatewayRequestMatcher()).permitAll()
                                .requestMatchers("/registration").permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .build();
    }

    private class GatewayRequestMatcher implements RequestMatcher {
        @Override
        public boolean matches(HttpServletRequest request) {
            return Objects.nonNull(request.getHeader(gatewayHeader)) && request.getHeader(gatewayHeader).equals(gatewaySecret);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
