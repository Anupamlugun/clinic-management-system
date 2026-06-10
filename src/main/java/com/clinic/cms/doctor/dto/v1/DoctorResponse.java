package com.clinic.cms.doctor.dto.v1;

import com.clinic.cms.doctor.enums.DoctorStatus;

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
        DoctorStatus status,
        Instant createdAt,
        Instant updatedAt
) {
}