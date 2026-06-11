package com.clinic.cms.billing.dto.v1;

import com.clinic.cms.billing.enums.PaymentMode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PaymentUpdateRequest(

        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
        BigDecimal amount,

        PaymentMode paymentMode,

        @Size(max = 500, message = "Remarks cannot exceed 500 characters")
        String remarks
) {
}