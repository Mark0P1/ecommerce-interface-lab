package com.ws101.maningcay.ecommerceapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserDto {

    @NotBlank
    @Size(min = 4)
    private String username;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    private String role;
}