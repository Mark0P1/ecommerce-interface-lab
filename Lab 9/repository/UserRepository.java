package com.ws101.maningcay.ecommerceapi.repository;

import com.ws101.maningcay.ecommerceapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User,String> {

    Optional<User> findByUsername(
            String username
    );
}