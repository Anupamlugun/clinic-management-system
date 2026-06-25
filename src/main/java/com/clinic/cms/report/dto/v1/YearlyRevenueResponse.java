package com.clinic.cms.report.dto.v1;

import java.math.BigDecimal;

public record YearlyRevenueResponse(

        Integer year,

        BigDecimal revenue

) {
}