package com.clinic.cms.doctor.dto.v1;


import com.clinic.cms.common.enums.WeekDay;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record DoctorScheduleUpdateRequest(

        @NotNull(message = "Day is required")
        WeekDay day,

        @NotNull(message = "Start time is required")
        LocalTime startTime,

        @NotNull(message = "End time is required")
        LocalTime endTime,

        Boolean active
) {
}