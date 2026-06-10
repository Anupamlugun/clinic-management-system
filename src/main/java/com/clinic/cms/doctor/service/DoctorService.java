package com.clinic.cms.doctor.service;

import com.clinic.cms.doctor.dto.v1.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {

    DoctorResponse createDoctor(DoctorCreateRequest request);

    DoctorResponse getDoctor(Long id);

    Page<DoctorResponse> getAllDoctors(Pageable pageable);

    DoctorResponse updateDoctor(Long id,
                                DoctorUpdateRequest request);

    void deleteDoctor(Long id);

    DoctorResponse updateDoctorStatus(Long id, DoctorStatusUpdateRequest request);

}
