package com.clinic.cms.appointment.service;

import com.clinic.cms.appointment.dto.v1.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {

    AppointmentResponse createAppointment(
            AppointmentCreateRequest request);

    AppointmentResponse getAppointment(
            Long id);

    Page<AppointmentResponse> getAllAppointments(
            Pageable pageable);

    AppointmentResponse updateAppointment(
            Long id,
            AppointmentUpdateRequest request);

    AppointmentResponse updateStatus(
            Long id,
            AppointmentStatusUpdateRequest request);
}