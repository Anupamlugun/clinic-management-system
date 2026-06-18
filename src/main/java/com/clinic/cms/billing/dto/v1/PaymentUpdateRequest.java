package com.clinic.cms.billing.dto.v1;

import com.clinic.cms.billing.enums.PaymentMode;
import com.clinic.cms.billing.enums.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PaymentUpdateRequest(


        @Size(max = 20)
        PaymentMode paymentMode,

        @Size(max = 20)
        PaymentStatus paymentStatus,

        @Size(max = 500, message = "Remarks cannot exceed 500 characters")
        String remarks
) {
}