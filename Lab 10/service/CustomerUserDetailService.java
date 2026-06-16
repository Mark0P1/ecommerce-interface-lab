package com.ws101.maningcay.ecommerceapi.service;

import com.ws101.maningcay.ecommerceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(
            String username
    ) throws UsernameNotFoundException {

        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        ));
    }
}