package com.clinic.cms.appointment.dto.v1;

import com.clinic.cms.appointment.enums.AppointmentStatus;
import jakarta.validation.constraints.NotNull;

public record AppointmentStatusUpdateRequest(

        @NotNull
        AppointmentStatus status
) {
}