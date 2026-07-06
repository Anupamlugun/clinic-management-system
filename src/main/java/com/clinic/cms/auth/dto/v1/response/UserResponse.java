package com.clinic.cms.auth.dto.v1.response;

import java.util.Set;

public record UserResponse(

        Long id,
        String username,
        String email,
        Boolean active,
        Set<RoleResponse> roles
) {
}