package com.clinic.cms.appointment.dto.v1;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentSlotResponse(

        Long id,

        Long doctorId,

        String doctorName,

        LocalDate slotDate,

        LocalTime startTime,

        LocalTime endTime,

        Boolean booked,

        Boolean active,

        Instant createdAt,

        Instant updatedAt

) {
}
