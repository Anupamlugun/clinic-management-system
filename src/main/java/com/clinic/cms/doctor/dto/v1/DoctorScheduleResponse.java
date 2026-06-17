package com.clinic.cms.doctor.dto.v1;

import com.clinic.cms.common.enums.WeekDay;

import java.time.Instant;
import java.time.LocalTime;

public record DoctorScheduleResponse(

        Long id,

        Long doctorId,

        String doctorName,

        WeekDay day,

        LocalTime startTime,

        LocalTime endTime,

        Boolean active,

        Instant createdAt,

        Instant updatedAt
) {
}