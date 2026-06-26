package com.clinic.cms.doctor.service.impl;

import com.clinic.cms.appointment.enums.AppointmentStatus;
import com.clinic.cms.appointment.repository.AppointmentRepository;
import com.clinic.cms.doctor.dto.v1.*;
import com.clinic.cms.doctor.entity.Doctor;
import com.clinic.cms.doctor.entity.DoctorSpecialization;
import com.clinic.cms.doctor.mapper.v1.DoctorMapper;
import com.clinic.cms.doctor.repository.DoctorRepository;
import com.clinic.cms.doctor.repository.DoctorSpecializationRepository;
import com.clinic.cms.doctor.service.DoctorService;
import com.clinic.cms.exception.custom.ResourceAlreadyExistsException;
import com.clinic.cms.exception.custom.ResourceNotFoundException;
import com.clinic.cms.exception.custom.ValidationException;
import com.clinic.cms.patient.dto.v1.PatientResponse;
import com.clinic.cms.patient.mapper.v1.PatientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository repository;
    private final DoctorMapper mapper;
    private final DoctorSpecializationRepository specializationRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientMapper patientMapper;

    @Override
    public DoctorResponse createDoctor(
            DoctorCreateRequest request) {

        if(repository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistsException(
                    "Doctor already exists");
        }

        Doctor doctor = mapper.toEntity(request);

        DoctorSpecialization specialization =
                specializationRepository.findById(
                                request.specializationId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Doctor specialization not found"));

        if (!specialization.getActive()) {
            throw new ValidationException(
                    "Doctor specialization is inactive");
        }

        doctor.setSpecialization(specialization);

        return mapper.toResponse(
                repository.save(doctor));
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponse getDoctor(Long id) {

        Doctor doctor = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found"));

        return mapper.toResponse(doctor);
    }

    @Override
    public Page<DoctorResponse> getAllDoctors(Pageable pageable) {

        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public DoctorResponse updateDoctor(
            Long id,
            DoctorUpdateRequest request) {

        Doctor doctor = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found"));

        DoctorSpecialization specialization =
                specializationRepository.findById(
                                request.specializationId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Doctor specialization not found"));

        if (!specialization.getActive()) {
            throw new ValidationException(
                    "Doctor specialization is inactive");
        }
        doctor.setFirstName(request.firstName());
        doctor.setLastName(request.lastName());
        doctor.setPhoneNumber(request.phoneNumber());
        doctor.setSpecialization(specialization);
        doctor.setExperienceYears(request.experienceYears());

        return mapper.toResponse(
                repository.save(doctor));
    }

    @Override
    public void deleteDoctor(Long id) {

        Doctor doctor = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found"));

        doctor.setActive(false);
        repository.save(doctor);
    }

    @Override
    public DoctorResponse updateDoctorStatus(Long id, DoctorStatusUpdateRequest request) {

        Doctor doctor = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found"
                        ));

        doctor.setStatus(request.status());
        return mapper.toResponse(repository.save(doctor));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse> getTopDoctors() {

        return repository.findTop5ByActiveTrueOrderByExperienceYearsDesc()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorResponse> getActiveDoctors(Pageable pageable) {

        return repository.findByActiveTrue(pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorResponse> getInactiveDoctors(Pageable pageable) {

        return repository.findByActiveFalse(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public DoctorResponse activateDoctor(Long id) {

        Doctor doctor = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found"));

        doctor.setActive(true);

        return mapper.toResponse(repository.save(doctor));
    }

    @Override
    public DoctorResponse deactivateDoctor(Long id) {

        Doctor doctor = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found"));

        doctor.setActive(false);

        return mapper.toResponse(repository.save(doctor));
    }
    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> getDoctorPatients(Long doctorId) {
        return appointmentRepository.findPatientsByDoctorId(doctorId)
                .stream()
                .map(patientMapper::toResponse)
                .toList();
    }

    @Override
    public DoctorStatisticsResponse getDoctorStatistics(Long doctorId) {
        long totalAppointments = appointmentRepository.countByDoctorId(doctorId);

        long completedAppointments =
                appointmentRepository.countByDoctorIdAndStatus(
                        doctorId,
                        AppointmentStatus.COMPLETED);

        long cancelledAppointments =
                appointmentRepository.countByDoctorIdAndStatus(
                        doctorId,
                        AppointmentStatus.CANCELLED);

        long todayAppointments =
                appointmentRepository.countByDoctorIdAndAppointmentDate(
                        doctorId,
                        LocalDate.now());

        long totalPatients =
                appointmentRepository.countDistinctPatientsByDoctorId(doctorId);

        return new DoctorStatisticsResponse(
                totalAppointments,
                completedAppointments,
                cancelledAppointments,
                todayAppointments,
                totalPatients
        );
    }

}
