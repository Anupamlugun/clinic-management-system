package com.clinic.cms.appointment.dto.v1;

import java.time.Instant;

public record PrescriptionMedicineResponse(

        Long id,

        Long prescriptionId,

        String medicineName,

        String dosage,

        String frequency,

        Integer durationDays,

        String instructions,

        Instant createdAt,

        Instant updatedAt
) {
}