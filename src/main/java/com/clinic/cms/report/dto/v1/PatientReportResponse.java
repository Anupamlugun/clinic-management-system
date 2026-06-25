package com.clinic.cms.report.dto.v1;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PatientReportResponse(

        Long patientId,

        String patientName,

        Long totalVisits,

        LocalDate lastVisit,

        BigDecimal totalPaid

) {
}