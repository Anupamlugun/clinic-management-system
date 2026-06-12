package com.clinic.cms.appointment.service.impl;

import com.clinic.cms.appointment.dto.v1.*;
import com.clinic.cms.appointment.entity.Appointment;
import com.clinic.cms.appointment.entity.AppointmentSlot;
import com.clinic.cms.appointment.enums.AppointmentStatus;
import com.clinic.cms.appointment.mapper.v1.AppointmentMapper;
import com.clinic.cms.appointment.repository.AppointmentRepository;
import com.clinic.cms.appointment.repository.AppointmentSlotRepository;
import com.clinic.cms.appointment.service.AppointmentService;
import com.clinic.cms.appointment.workflow.AppointmentWorkflowRules;
import com.clinic.cms.doctor.entity.Doctor;
import com.clinic.cms.doctor.repository.DoctorRepository;
import com.clinic.cms.exception.custom.ResourceNotFoundException;
import com.clinic.cms.patient.entity.Patient;
import com.clinic.cms.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl
        implements AppointmentService {

    private final AppointmentRepository repository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentSlotRepository slotRepository;
    private final AppointmentMapper mapper;

    @Override
    public AppointmentResponse createAppointment(
            AppointmentCreateRequest request) {

        Patient patient = patientRepository.findById(
                        request.patientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found"));

        Doctor doctor = doctorRepository.findById(
                        request.doctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found"));

        AppointmentSlot slot = slotRepository.findById(
                        request.slotId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment slot not found"));

        Appointment appointment =
                mapper.toEntity(request);

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setSlot(slot);

        appointment.setAppointmentDate(
                slot.getSlotDate());

        appointment.setStatus(
                AppointmentStatus.CONFIRMED);

        if (Boolean.TRUE.equals(request.followUp())
                && request.parentAppointmentId() != null) {

            Appointment parent =
                    repository.findById(
                                    request.parentAppointmentId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Parent appointment not found"));

            appointment.setParentAppointment(parent);
        }

        return mapper.toResponse(
                repository.save(appointment));
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponse getAppointment(
            Long id) {

        Appointment appointment =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Appointment not found"));

        return mapper.toResponse(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentResponse> getAllAppointments(
            Pageable pageable) {

        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public AppointmentResponse updateAppointment(
            Long id,
            AppointmentUpdateRequest request) {

        Appointment appointment =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Appointment not found"));

        AppointmentSlot slot =
                slotRepository.findById(
                                request.slotId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Appointment slot not found"));

        mapper.updateEntityFromRequest(
                request,
                appointment);

        appointment.setSlot(slot);
        appointment.setAppointmentDate(
                slot.getSlotDate());

        return mapper.toResponse(
                repository.save(appointment));
    }

    @Override
    public AppointmentResponse updateStatus(
            Long id,
            AppointmentStatusUpdateRequest request) {

        Appointment appointment =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Appointment not found"));

        AppointmentWorkflowRules
                .validateTransition(
                        appointment.getStatus(),
                        request.status());

        appointment.setStatus(
                request.status());

        return mapper.toResponse(
                repository.save(appointment));
    }
}