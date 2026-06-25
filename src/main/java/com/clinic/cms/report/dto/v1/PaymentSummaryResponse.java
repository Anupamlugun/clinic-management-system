package com.clinic.cms.report.dto.v1;

import java.math.BigDecimal;

public record PaymentSummaryResponse(

        Long totalPayments,

        Long completedPayments,

        Long pendingPayments,

        Long failedPayments,

        Long refundedPayments,

        BigDecimal collectedAmount,

        BigDecimal pendingAmount

) {
}