package com.clinic.cms.report.dto.v1;

import java.math.BigDecimal;

public record RevenueReportResponse(

        BigDecimal todayRevenue,

        BigDecimal weeklyRevenue,

        BigDecimal monthlyRevenue,

        BigDecimal yearlyRevenue,

        BigDecimal totalRevenue

) {
}