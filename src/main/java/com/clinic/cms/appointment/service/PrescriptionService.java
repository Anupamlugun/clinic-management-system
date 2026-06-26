package com.clinic.cms.appointment.service;

import com.clinic.cms.appointment.dto.v1.PrescriptionCreateRequest;
import com.clinic.cms.appointment.dto.v1.PrescriptionResponse;
import com.clinic.cms.appointment.dto.v1.PrescriptionUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PrescriptionService {

    PrescriptionResponse createPrescription(
            PrescriptionCreateRequest request);

    PrescriptionResponse getPrescription(
            Long id);

    Page<PrescriptionResponse> getAllPrescriptions(
            Pageable pageable);

    PrescriptionResponse updatePrescription(
            Long id,
            PrescriptionUpdateRequest request);

    void deletePrescription(
            Long id);

    Page<PrescriptionResponse> getPrescriptionsByPatient(
            Long patientId,
            Pageable pageable);

    Page<PrescriptionResponse> getPrescriptionsByDoctor(
            Long doctorId,
            Pageable pageable);

    PrescriptionResponse getPrescriptionByAppointment(
            Long appointmentId);

    Page<PrescriptionResponse> getFollowUpPrescriptions(
            Pageable pageable);
}