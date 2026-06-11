package com.clinic.cms.exception.custom;

import com.clinic.cms.appointment.enums.AppointmentStatus;

public class InvalidAppointmentStatusTransitionException
        extends RuntimeException {

    public InvalidAppointmentStatusTransitionException(
            AppointmentStatus currentStatus,
            AppointmentStatus targetStatus) {

        super(String.format(
                "Invalid appointment status transition from '%s' to '%s'",
                currentStatus,
                targetStatus
        ));
    }
}