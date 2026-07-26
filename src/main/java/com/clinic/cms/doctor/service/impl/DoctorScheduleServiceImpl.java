package com.clinic.cms.doctor.service.impl;

import com.clinic.cms.auth.security.CurrentUserService;
import com.clinic.cms.doctor.enums.DoctorStatus;
import com.clinic.cms.exception.custom.ResourceAlreadyExistsException;
import com.clinic.cms.exception.custom.ResourceNotFoundException;
import com.clinic.cms.doctor.dto.v1.DoctorScheduleCreateRequest;
import com.clinic.cms.doctor.dto.v1.DoctorScheduleResponse;
import com.clinic.cms.doctor.dto.v1.DoctorScheduleUpdateRequest;
import com.clinic.cms.doctor.entity.Doctor;
import com.clinic.cms.doctor.entity.DoctorSchedule;
import com.clinic.cms.doctor.mapper.v1.DoctorScheduleMapper;
import com.clinic.cms.doctor.repository.DoctorRepository;
import com.clinic.cms.doctor.repository.DoctorScheduleRepository;
import com.clinic.cms.doctor.service.DoctorScheduleService;
import com.clinic.cms.exception.custom.ValidationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository scheduleRepository;
    private final DoctorScheduleMapper scheduleMapper;
    private final CurrentUserService currentUserService;

    @Override
    public DoctorScheduleResponse createSchedule(
            DoctorScheduleCreateRequest request) {

        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with id: "
                                        + request.doctorId()));

        if (currentUserService.hasRole("DOCTOR")
                && !doctor.getUser().getId().equals(currentUserService.getUserId())) {

            throw new AccessDeniedException(
                    "You cannot create schedules for another doctor.");
        }

        if (!doctor.getActive()) {
            throw new ValidationException("Doctor is inactive");
        }

        if (doctor.getStatus() != DoctorStatus.ACTIVE) {
            throw new ValidationException(doctor.getStatus().getDescription());
        }

        if (scheduleRepository.existsByDoctorIdAndDay(
                request.doctorId(),
                request.day())) {

            throw new ResourceAlreadyExistsException(
                    "Schedule already exists for doctor on "
                            + request.day());
        }

        DoctorSchedule schedule =
                scheduleMapper.toEntity(request, doctor);

        return scheduleMapper.toResponse(
                scheduleRepository.save(schedule));
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public DoctorScheduleResponse getSchedule(
            Long id) {

        DoctorSchedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Schedule not found with id: " + id));

        checkDoctorScheduleAccess(schedule);

        return scheduleMapper.toResponse(schedule);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Page<DoctorScheduleResponse> getAllSchedules(
            Pageable pageable) {

        return scheduleRepository.findAll(pageable)
                .map(scheduleMapper::toResponse);
    }

    @Override
    public DoctorScheduleResponse updateSchedule(
            Long id,
            DoctorScheduleUpdateRequest request) {

        DoctorSchedule schedule =
                scheduleRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Schedule not found with id: "
                                                + id));

        checkDoctorScheduleAccess(schedule);

        schedule.setDay(request.day());
        schedule.setStartTime(request.startTime());
        schedule.setEndTime(request.endTime());

        return scheduleMapper.toResponse(
                scheduleRepository.save(schedule));
    }

    @Override
    public void deleteSchedule(
            Long id) {

        DoctorSchedule schedule =
                scheduleRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Schedule not found with id: "
                                                + id));
        checkDoctorScheduleAccess(schedule);
        scheduleRepository.delete(schedule);
    }

    private void checkDoctorScheduleAccess(DoctorSchedule schedule) {

        if (currentUserService.hasRole("SYSTEM_ADMIN")
                || currentUserService.hasRole("RECEPTIONIST")) {
            return;
        }

        if (currentUserService.hasRole("DOCTOR")) {

            Long currentUserId = currentUserService.getUserId();

            if (!schedule.getDoctor().getUser().getId().equals(currentUserId)) {
                throw new AccessDeniedException(
                        "You are not allowed to access this doctor's schedule.");
            }

            return;
        }

        throw new AccessDeniedException("Access denied.");
    }
}