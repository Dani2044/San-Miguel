package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTAuthEntryPoint jwtAuthEntryPoint;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
        .authorizeHttpRequests(requests -> requests
            // Allow H2 console and admin login without auth
            .requestMatchers("/h2-console/**").permitAll()
            .requestMatchers("/api/administrators/login").permitAll()
            // Allow public GET access to events (useful for frontend public listing).
            // If you want events protected, remove this matcher.
            .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/events", "/api/events/**").permitAll()
            // Allow public GET access to galleries so frontend can fetch images without auth
            .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/galleries", "/api/galleries/**").permitAll()
            // Also allow legacy/singular path used by the frontend in some places
            .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/gallery", "/api").permitAll()
            // Allow public access to image upload/download endpoints and uploaded files
            .requestMatchers(org.springframework.http.HttpMethod.POST, "/image", "/image/**").permitAll()
            .requestMatchers(org.springframework.http.HttpMethod.GET, "/image/**", "/uploads/**").permitAll()
            .anyRequest().authenticated())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint));

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter();
    }
}