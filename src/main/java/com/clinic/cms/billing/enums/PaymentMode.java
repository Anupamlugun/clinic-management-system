package com.clinic.cms.billing.enums;

import lombok.Getter;

@Getter
public enum PaymentMode {

    CASH("Cash"),
    UPI("UPI"),
    CARD("Card"),
    NET_BANKING("Net Banking"),
    WALLET("Wallet");

    private final String displayName;

    PaymentMode(String displayName) {
        this.displayName = displayName;
    }
}