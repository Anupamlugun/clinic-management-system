package com.clinic.cms.appointment.dto.v1;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PrescriptionCreateRequest(

        @NotNull(message = "Appointment id is required")
        Long appointmentId,

        @NotNull(message = "Doctor id is required")
        Long doctorId,

        @NotNull(message = "Patient id is required")
        Long patientId,

        @Size(max = 5000, message = "Diagnosis cannot exceed 5000 characters")
        String diagnosis,

        @Size(max = 5000, message = "Notes cannot exceed 5000 characters")
        String notes,

        LocalDate followUpDate
) {
}