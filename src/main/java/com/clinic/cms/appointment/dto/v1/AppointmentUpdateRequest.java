package com.clinic.cms.appointment.dto.v1;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AppointmentUpdateRequest(

        @NotNull(message = "Slot id is required")
        Long slotId,

        @Size(max = 1000)
        String reason,

        Boolean followUp
) {
}