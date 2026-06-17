package com.clinic.cms.doctor.service;

import com.clinic.cms.doctor.dto.v1.DoctorScheduleCreateRequest;
import com.clinic.cms.doctor.dto.v1.DoctorScheduleResponse;
import com.clinic.cms.doctor.dto.v1.DoctorScheduleUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorScheduleService {

    DoctorScheduleResponse createSchedule(
            DoctorScheduleCreateRequest request);

    DoctorScheduleResponse getSchedule(
            Long id);

    Page<DoctorScheduleResponse> getAllSchedules(
            Pageable pageable);

    DoctorScheduleResponse updateSchedule(
            Long id,
            DoctorScheduleUpdateRequest request);

    void deleteSchedule(
            Long id);
}