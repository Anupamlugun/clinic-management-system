package com.clinic.cms.report.dto.v1;

import java.math.BigDecimal;

public record MonthlyRevenueResponse(

        Integer year,

        Integer month,

        BigDecimal revenue

) {
}