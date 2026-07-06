package com.clinic.cms.auth.dto.v1.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record UserUpdateRequest(

        @NotBlank(message = "Username is required")
        String username,

        @Email(message = "Invalid email")
        @NotBlank(message = "Email is required")
        String email,

        Boolean active,

        Set<Long> roleIds
) {
}