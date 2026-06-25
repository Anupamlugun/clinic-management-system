package com.clinic.cms.report.dto.v1;

import java.math.BigDecimal;

public record TodayReportResponse(

        Long totalAppointments,

        Long completedAppointments,

        Long cancelledAppointments,

        Long pendingAppointments,

        BigDecimal todayRevenue

) {
}