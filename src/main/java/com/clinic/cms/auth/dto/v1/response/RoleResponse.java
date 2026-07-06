package com.clinic.cms.auth.dto.v1.response;

import java.util.Set;

public record RoleResponse(

        Long id,
        String code,
        String name,
        String description,
        Set<PermissionResponse> permissions
) {
}