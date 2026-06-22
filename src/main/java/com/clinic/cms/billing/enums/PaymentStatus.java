package com.clinic.cms.billing.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {

    PENDING(
            "Pending",
            "Payment has been initiated but is not yet completed"
    ),

    COMPLETED(
            "Completed",
            "Payment has been successfully completed"
    ),

    FAILED(
            "Failed",
            "Payment attempt was unsuccessful"
    ),

    REFUNDED(
            "Refunded",
            "Payment amount has been refunded to the payer"
    );

    private final String displayName;
    private final String description;

    PaymentStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}