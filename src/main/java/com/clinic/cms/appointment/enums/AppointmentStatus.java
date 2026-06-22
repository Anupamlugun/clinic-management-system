package com.clinic.cms.appointment.enums;

import lombok.Getter;

@Getter
public enum AppointmentStatus {

    CONFIRMED(
            "Confirmed",
            "Appointment has been confirmed and scheduled"
    ),

    CHECKED_IN(
            "Checked In",
            "Patient has arrived and checked in at the clinic"
    ),

    IN_CONSULTATION(
            "In Consultation",
            "Patient is currently consulting with the doctor"
    ),

    COMPLETED(
            "Completed",
            "Consultation and appointment have been completed"
    ),

    FOLLOW_UP_SCHEDULED(
            "Follow Up Scheduled",
            "A follow-up appointment has been scheduled"
    ),

    CANCELLED(
            "Cancelled",
            "Appointment has been cancelled"
    ),

    NO_SHOW(
            "No Show",
            "Patient did not arrive for the scheduled appointment"
    );

    private final String displayName;
    private final String description;

    AppointmentStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}