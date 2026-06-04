package com.clinic.cms.doctor.dto;

import jakarta.validation.constraints.*;

public record DoctorCreateRequest(

        @NotBlank(message = "First name is required")
        @Size(max = 100)
        String firstName,

        @Size(max = 100)
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 255)
        String email,

        @Size(max = 20)
        String phoneNumber,

        Long specializationId,

        @NotNull(message = "Experience years is required")
        @PositiveOrZero(message = "Experience years cannot be negative")
        Integer experienceYears

) {}