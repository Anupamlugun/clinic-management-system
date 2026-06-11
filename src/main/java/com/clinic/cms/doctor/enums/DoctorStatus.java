package com.clinic.cms.doctor.enums;

import lombok.Getter;

@Getter
public enum DoctorStatus {

    ACTIVE("Active"),
    ON_LEAVE("On Leave"),
    SUSPENDED("Suspended"),
    RESIGNED("Resigned");

    private final String displayName;

    DoctorStatus(String displayName) {
        this.displayName = displayName;
    }

}