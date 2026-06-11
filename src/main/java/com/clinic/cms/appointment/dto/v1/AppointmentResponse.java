package com.clinic.cms.appointment.dto.v1;

import com.clinic.cms.appointment.enums.AppointmentStatus;

import java.time.Instant;
import java.time.LocalDate;

public record AppointmentResponse(

        Long id,

        Long patientId,
        String patientName,

        Long doctorId,
        String doctorName,

        Long slotId,
        String slotTime,

        LocalDate appointmentDate,

        AppointmentStatus status,

        String reason,

        Boolean followUp,

        Long parentAppointmentId,

        Instant createdAt,
        Instant updatedAt
) {
}
