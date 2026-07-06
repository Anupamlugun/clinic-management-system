package com.clinic.cms.auth.dto.v1.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record RoleUpdateRequest(

        @NotBlank(message = "Code is required")
        String code,

        @NotBlank(message = "Name is required")
        String name,

        String description,

        Set<Long> permissionIds
) {
}