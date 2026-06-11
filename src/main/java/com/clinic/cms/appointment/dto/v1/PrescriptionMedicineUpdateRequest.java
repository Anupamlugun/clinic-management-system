package com.clinic.cms.appointment.dto.v1;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record PrescriptionMedicineUpdateRequest(

        @Size(max = 150, message = "Medicine name cannot exceed 150 characters")
        String medicineName,

        @Size(max = 100, message = "Dosage cannot exceed 100 characters")
        String dosage,

        @Size(max = 100, message = "Frequency cannot exceed 100 characters")
        String frequency,

        @Min(value = 1, message = "Duration must be at least 1 day")
        Integer durationDays,

        @Size(max = 500, message = "Instructions cannot exceed 500 characters")
        String instructions
) {
}