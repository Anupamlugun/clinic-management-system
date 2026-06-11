package com.clinic.cms.appointment.dto.v1;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PrescriptionUpdateRequest(

        @Size(max = 5000, message = "Diagnosis cannot exceed 5000 characters")
        String diagnosis,

        @Size(max = 5000, message = "Notes cannot exceed 5000 characters")
        String notes,

        LocalDate followUpDate
) {
}