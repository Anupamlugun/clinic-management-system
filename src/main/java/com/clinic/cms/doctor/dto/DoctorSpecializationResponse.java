package com.clinic.cms.doctor.dto;

import java.time.Instant;

public record DoctorSpecializationResponse(
        Long id,
        Long version,
        String code,
        String name,
        Boolean active,
        Instant createdAt,
        Instant updatedAt,
        String createdBy,
        String updatedBy
) {
}