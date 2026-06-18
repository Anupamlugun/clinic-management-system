package com.clinic.cms.billing.dto.v1;

import com.clinic.cms.billing.enums.PaymentMode;
import com.clinic.cms.billing.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

public record PaymentResponse(

        Long id,

        Long appointmentId,

        BigDecimal amount,

        LocalDateTime paidAt,

        String receiptNumber,

        PaymentMode paymentMode,

        PaymentStatus paymentStatus,

        String remarks,

        Instant createdAt,

        Instant updatedAt
) {
}