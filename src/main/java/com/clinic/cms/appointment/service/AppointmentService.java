package com.clinic.cms.appointment.service;

import com.clinic.cms.appointment.dto.v1.*;
import com.clinic.cms.appointment.enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

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

    Page<AppointmentResponse> getAppointmentsByPatient(Long patientId, Pageable pageable);

    Page<AppointmentResponse> getAppointmentsByDoctor(Long doctorId, Pageable pageable);

    Page<AppointmentResponse> getAppointmentsByStatus(AppointmentStatus status, Pageable pageable);

    Page<AppointmentResponse> getAppointmentsByDate(LocalDate date, Pageable pageable);

    Page<AppointmentResponse> getTodayAppointments(Pageable pageable);

    Page<AppointmentResponse> getUpcomingAppointments(Pageable pageable);

    Page<AppointmentResponse> getAppointmentHistory(Long patientId, Pageable pageable);
}