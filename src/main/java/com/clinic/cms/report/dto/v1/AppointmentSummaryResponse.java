package com.clinic.cms.report.dto.v1;

public record AppointmentSummaryResponse(

        Long totalAppointments,

        Long scheduled,

        Long checkedIn,

        Long inConsultation,

        Long completed,

        Long cancelled,

        Long noShow

) {
}