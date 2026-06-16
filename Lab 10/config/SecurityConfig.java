package com.ws101.maningcay.ecommerceapi.config;

import com.ws101.maningcay.ecommerceapi.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(
                                        "/api/v1/auth/register"
                                ).permitAll()

                                .requestMatchers(
                                        "/api/v1/products"
                                ).permitAll()

                                .anyRequest()
                                .authenticated()
                )

                .formLogin(
                        Customizer.withDefaults()
                )

                .logout(
                        Customizer.withDefaults()
                );

        return http.build();
    }
}

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http
    )
            throws Exception {

        http
                .csrf(
                        csrf ->
                                csrf.disable()
                )

                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS
                                )
                )

                .authorizeHttpRequests(
                        auth ->
                                auth
                                        .requestMatchers(
                                                "/api/v1/auth/**"
                                        )
                                        .permitAll()

                                        .anyRequest()
                                        .authenticated()
                )

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider
    authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(
                userDetailsService
        );

        provider.setPasswordEncoder(
                passwordEncoder()
        );

        return provider;
    }
}