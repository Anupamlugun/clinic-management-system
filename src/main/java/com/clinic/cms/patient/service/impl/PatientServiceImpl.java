package com.clinic.cms.patient.service.impl;

import com.clinic.cms.appointment.dto.v1.AppointmentResponse;
import com.clinic.cms.appointment.mapper.v1.AppointmentMapper;
import com.clinic.cms.appointment.repository.AppointmentRepository;
import com.clinic.cms.auth.entity.User;
import com.clinic.cms.auth.service.UserService;
import com.clinic.cms.billing.dto.v1.PaymentResponse;
import com.clinic.cms.billing.mapper.v1.PaymentMapper;
import com.clinic.cms.billing.repository.PaymentRepository;
import com.clinic.cms.exception.custom.ResourceAlreadyExistsException;
import com.clinic.cms.exception.custom.ResourceNotFoundException;
import com.clinic.cms.patient.dto.v1.PatientCreateRequest;
import com.clinic.cms.patient.dto.v1.PatientResponse;
import com.clinic.cms.patient.dto.v1.PatientUpdateRequest;
import com.clinic.cms.patient.entity.Patient;
import com.clinic.cms.patient.mapper.v1.PatientMapper;
import com.clinic.cms.patient.repository.PatientRepository;
import com.clinic.cms.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;
    private final PatientMapper mapper;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public PatientResponse createPatient(
            PatientCreateRequest request) {

        if (repository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistsException(
                    "Patient already exists");
        }

        Patient patient = mapper.toEntity(request);

        Patient savedPatient = repository.save(patient);

        User userRequest = User.builder()
                .username("PAT" + savedPatient.getId())
                .email(savedPatient.getEmail())
                .password(passwordEncoder.encode("1234"))
                .roleIds(Set.of(4L))
                .build();

        User savedUser = userService.createUser(userRequest);
        savedPatient.setUser(savedUser);
        return mapper.toResponse(savedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatient(Long id) {

        Patient patient = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found"));

        return mapper.toResponse(patient);
    }

    @Override
    public Page<PatientResponse> getAllPatients(
            Pageable pageable) {

        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public PatientResponse updatePatient(
            Long id,
            PatientUpdateRequest request) {

        Patient patient = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found"));

        patient.setFirstName(request.firstName());
        patient.setLastName(request.lastName());
        patient.setPhoneNumber(request.phoneNumber());
        patient.setDateOfBirth(request.dateOfBirth());
        patient.setGender(request.gender());
        patient.setBloodGroup(request.bloodGroup());
        patient.setEmergencyContactName(
                request.emergencyContactName());
        patient.setEmergencyContactNumber(
                request.emergencyContactNumber());
        patient.setAddress(request.address());

        return mapper.toResponse(
                repository.save(patient));
    }

    @Override
    public void deletePatient(Long id) {

        Patient patient = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found"));

        patient.setActive(false);

        repository.save(patient);
    }

    @Override
    public PatientResponse activatePatient(Long id) {

        Patient patient = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found"));

        patient.setActive(true);

        return mapper.toResponse(repository.save(patient));
    }

    @Override
    public PatientResponse deactivatePatient(Long id) {

        Patient patient = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found"));

        patient.setActive(false);

        return mapper.toResponse(repository.save(patient));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientResponse> searchPatients(
            String keyword,
            Pageable pageable) {

        if (keyword == null || keyword.isBlank()) {
            return repository.findAll(pageable)
                    .map(mapper::toResponse);
        }

        return repository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(
                        keyword,
                        keyword,
                        keyword,
                        pageable)
                .map(mapper::toResponse);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getPatientPayments(
            Long patientId,
            Pageable pageable) {

        getPatient(patientId);

        return paymentRepository
                .findByAppointmentPatientId(patientId, pageable)
                .map(paymentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentResponse> getPatientAppointments(
            Long patientId,
            Pageable pageable) {

        getPatient(patientId);

        return appointmentRepository
                .findByPatientId(patientId, pageable)
                .map(appointmentMapper::toResponse);
    }
}