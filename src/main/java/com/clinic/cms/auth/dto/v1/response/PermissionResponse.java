package com.clinic.cms.auth.dto.v1.response;

public record PermissionResponse(

        Long id,
        String code,
        String name,
        String description
) {
}