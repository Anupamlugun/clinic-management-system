package com.clinic.cms.appointment.enums;

import lombok.Getter;

@Getter
public enum AppointmentStatus {

    PENDING("Pending"),
    CONFIRMED("Confirmed"),

    PAYMENT_PENDING("Payment Pending"),
    PAYMENT_COMPLETED("Payment Completed"),

    CHECKED_IN("Checked In"),

    IN_CONSULTATION("In Consultation"),

    COMPLETED("Completed"),

    FOLLOW_UP_SCHEDULED("Follow Up Scheduled"),

    CANCELLED("Cancelled"),

    NO_SHOW("No Show");

    private final String displayName;

    AppointmentStatus(String displayName) {
        this.displayName = displayName;
    }
}
