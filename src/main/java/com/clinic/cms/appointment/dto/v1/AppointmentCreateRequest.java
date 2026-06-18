package com.clinic.cms.appointment.dto.v1;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AppointmentCreateRequest(

        @NotNull(message = "Patient id is required")
        Long patientId,

        @NotNull(message = "Doctor id is required")
        Long doctorId,

        @NotNull(message = "Slot id is required")
        Long slotId,

        @Size(max = 1000)
        String reason,

        Boolean followUp,

        Long parentAppointmentId
) {
}
