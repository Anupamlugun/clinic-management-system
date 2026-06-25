package com.clinic.cms.report.dto.v1;

import java.math.BigDecimal;

public record DoctorReportResponse(

        Long doctorId,

        String doctorName,

        Long totalAppointments,

        Long completedAppointments,

        Long cancelledAppointments,

        BigDecimal revenue

) {
}