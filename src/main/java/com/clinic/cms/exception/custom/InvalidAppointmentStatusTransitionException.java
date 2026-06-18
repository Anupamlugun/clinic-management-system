package com.clinic.cms.exception.custom;

import com.clinic.cms.appointment.enums.AppointmentStatus;

import java.util.Set;
import java.util.stream.Collectors;

public class InvalidAppointmentStatusTransitionException
        extends RuntimeException {

    public InvalidAppointmentStatusTransitionException(
            AppointmentStatus currentStatus,
            AppointmentStatus targetStatus,
            Set<AppointmentStatus> allowedStatuses) {

        super(String.format(
                "Appointment cannot be moved from '%s' to '%s'. Allowed next status(es): %s.",
                currentStatus.getDisplayName(),
                targetStatus.getDisplayName(),
                allowedStatuses.stream()
                        .map(AppointmentStatus::getDisplayName)
                        .collect(Collectors.joining(", "))
        ));
    }
}