package com.clinic.cms.doctor.dto.v1;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record DoctorUpdateRequest(

        @Size(max = 100)
        String firstName,

        @Size(max = 100)
        String lastName,

        @Size(max = 20)
        String phoneNumber,

        Long specializationId,

        @PositiveOrZero(message = "Experience years cannot be negative")
        Integer experienceYears,

        Boolean active

) {
}