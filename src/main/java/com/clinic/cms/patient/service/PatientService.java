package com.clinic.cms.patient.service;

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
}