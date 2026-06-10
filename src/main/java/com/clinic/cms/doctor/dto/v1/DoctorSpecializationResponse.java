package com.clinic.cms.doctor.dto.v1;

import java.time.Instant;

public record DoctorSpecializationResponse(
        Long id,
        String code,
        String name,
        Boolean active,
        Instant createdAt,
        Instant updatedAt
) {
}