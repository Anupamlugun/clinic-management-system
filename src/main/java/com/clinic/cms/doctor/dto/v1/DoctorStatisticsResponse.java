package com.clinic.cms.doctor.dto.v1;

public record DoctorStatisticsResponse(
        Long totalAppointments,
        Long completedAppointments,
        Long cancelledAppointments,
        Long todayAppointments,
        Long totalPatients
) {}
