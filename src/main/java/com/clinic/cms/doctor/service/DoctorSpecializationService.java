package com.clinic.cms.doctor.service;

import com.clinic.cms.doctor.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorSpecializationService {

    DoctorSpecializationResponse createSpecialization(
            DoctorSpecializationCreateRequest request);

    DoctorSpecializationResponse getSpecialization(
            Long id);

    Page<DoctorSpecializationResponse> getAllSpecializations(
            Pageable pageable);

    DoctorSpecializationResponse updateSpecialization(
            Long id,
            DoctorSpecializationUpdateRequest request);

    void deleteSpecialization(Long id);
}