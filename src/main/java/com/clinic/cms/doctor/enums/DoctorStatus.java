package com.clinic.cms.doctor.enums;

import lombok.Getter;

@Getter
public enum DoctorStatus {

    ACTIVE(
            "Active",
            "Doctor is available for appointments and consultations"
    ),
    ON_LEAVE(
            "On Leave",
            "Doctor is temporarily unavailable due to leave"
    ),
    SUSPENDED(
            "Suspended",
            "Doctor account is suspended and cannot provide services"
    ),
    RESIGNED(
            "Resigned",
            "Doctor has permanently left the clinic"
    );

    private final String displayName;
    private final String description;

    DoctorStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}