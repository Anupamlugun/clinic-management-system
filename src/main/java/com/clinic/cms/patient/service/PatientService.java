package com.clinic.cms.patient.service;

import com.clinic.cms.appointment.dto.v1.AppointmentResponse;
import com.clinic.cms.billing.dto.v1.PaymentResponse;
import com.clinic.cms.patient.dto.v1.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {

    PatientResponse createPatient(
            PatientCreateRequest request);

    PatientResponse getPatient(Long id);

    Page<PatientResponse> getAllPatients(
            Pageable pageable);

    PatientResponse updatePatient(
            Long id,
            PatientUpdateRequest request);

    void deletePatient(Long id);

    Page<PatientResponse> searchPatients(
            String keyword,
            Pageable pageable);

    Page<PaymentResponse> getPatientPayments(
            Long patientId,
            Pageable pageable);

    Page<AppointmentResponse> getPatientAppointments(
            Long patientId,
            Pageable pageable);

    PatientResponse activatePatient(
            Long id);

    PatientResponse deactivatePatient(
            Long id);
}