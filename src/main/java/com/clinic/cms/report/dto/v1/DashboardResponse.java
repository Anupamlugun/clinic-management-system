package com.clinic.cms.report.dto.v1;

import java.math.BigDecimal;

public record DashboardResponse(

        Long totalPatients,

        Long totalDoctors,

        Long totalAppointments,

        Long todayAppointments,

        Long completedAppointments,

        Long cancelledAppointments,

        BigDecimal totalRevenue,

        BigDecimal todayRevenue

) {
}