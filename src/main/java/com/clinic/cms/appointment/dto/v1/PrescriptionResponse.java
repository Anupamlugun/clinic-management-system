package com.clinic.cms.appointment.dto.v1;

import java.time.Instant;
import java.time.LocalDate;

public record PrescriptionResponse(

        Long id,

        Long appointmentId,

        Long doctorId,
        String doctorName,

        Long patientId,
        String patientName,

        String diagnosis,

        String notes,

        LocalDate followUpDate,

        Instant createdAt,

        Instant updatedAt
) {
}