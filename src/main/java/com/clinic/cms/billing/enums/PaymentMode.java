package com.clinic.cms.billing.enums;

import lombok.Getter;

@Getter
public enum PaymentMode {

    CASH(
            "Cash",
            "Payment made using physical cash"
    ),

    UPI(
            "UPI",
            "Payment made through a Unified Payments Interface application"
    ),

    CARD(
            "Card",
            "Payment made using a debit card or credit card"
    ),

    NET_BANKING(
            "Net Banking",
            "Payment made through the bank's online banking portal"
    ),

    WALLET(
            "Wallet",
            "Payment made using a digital wallet application"
    );

    private final String displayName;
    private final String description;

    PaymentMode(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}