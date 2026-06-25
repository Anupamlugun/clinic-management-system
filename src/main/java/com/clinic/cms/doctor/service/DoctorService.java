package com.clinic.cms.doctor.service;

import com.clinic.cms.doctor.dto.v1.*;
import com.clinic.cms.patient.dto.v1.PatientResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoctorService {

    DoctorResponse createDoctor(DoctorCreateRequest request);

    DoctorResponse getDoctor(Long id);

    Page<DoctorResponse> getAllDoctors(Pageable pageable);

    DoctorResponse updateDoctor(Long id,
                                DoctorUpdateRequest request);

    void deleteDoctor(Long id);

    DoctorResponse updateDoctorStatus(Long id, DoctorStatusUpdateRequest request);

    List<DoctorResponse> getTopDoctors();

    Page<DoctorResponse> getActiveDoctors(Pageable pageable);

    Page<DoctorResponse> getInactiveDoctors(Pageable pageable);

    List<PatientResponse> getDoctorPatients(Long doctorId);

    DoctorStatisticsResponse getDoctorStatistics(Long doctorId);

    DoctorResponse activateDoctor(Long id);

    DoctorResponse deactivateDoctor(Long id);

}
