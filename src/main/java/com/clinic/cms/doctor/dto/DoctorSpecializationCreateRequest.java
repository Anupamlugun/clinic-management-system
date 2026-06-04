package com.clinic.cms.doctor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DoctorSpecializationCreateRequest(

        @NotBlank(message = "Code is required")
        @Size(max = 50, message = "Code cannot exceed 50 characters")
        String code,

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name cannot exceed 100 characters")
        String name

) {
}