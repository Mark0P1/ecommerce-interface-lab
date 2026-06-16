package com.ws101.maningcay.ecommerceapi.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ws101.maningcay.ecommerceapi.service.CustomUserDetailsService;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final CustomUserDetailsService userService;

    public JwtAuthenticationFilter(
            JwtUtil jwtUtil,
            CustomUserDetailsService userService
    ) {

        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )
            throws ServletException,
            IOException {

        String header =
                request.getHeader(
                        "Authorization"
                );

        if (
                header == null ||
                        !header.startsWith(
                                "Bearer "
                        )
        ) {

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        String token =
                header.substring(7);

        String username =
                jwtUtil.extractUsername(
                        token
                );

        UserDetails user =
                userService.loadUserByUsername(
                        username
                );

        if (
                jwtUtil.isTokenValid(
                        token,
                        user
                )
        ) {

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );

            auth.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(
                                    request
                            )
            );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(
                            auth
                    );
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}