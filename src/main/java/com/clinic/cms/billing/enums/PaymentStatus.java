package com.clinic.cms.billing.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {

    PENDING("Pending"),

    COMPLETED("Completed"),

    FAILED("Failed"),

    REFUNDED("Refunded");

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }
}