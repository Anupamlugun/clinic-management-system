package com.clinic.cms.auth.dto.v1.request;

import jakarta.validation.constraints.NotBlank;

public record PermissionCreateRequest(

        @NotBlank(message = "Code is required")
        String code,

        @NotBlank(message = "Name is required")
        String name,

        String description
) {
}