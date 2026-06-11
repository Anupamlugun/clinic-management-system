package com.clinic.cms.billing.dto.v1;

import com.clinic.cms.billing.enums.PaymentMode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PaymentCreateRequest(

        @NotNull(message = "Appointment id is required")
        Long appointmentId,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotNull(message = "Payment mode is required")
        PaymentMode paymentMode,

        @Size(max = 500, message = "Remarks cannot exceed 500 characters")
        String remarks
) {
}