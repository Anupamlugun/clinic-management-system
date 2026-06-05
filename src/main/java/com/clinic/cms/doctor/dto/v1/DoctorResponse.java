package com.clinic.cms.doctor.dto.v1;

import java.time.Instant;

public record DoctorResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        DoctorSpecializationResponse specialization,
        Integer experienceYears,
        Boolean active,
        Instant createdAt,
        Instant updatedAt
) {
}