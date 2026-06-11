package com.clinic.cms.patient.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;
    private final PatientMapper mapper;

    @Override
    public PatientResponse createPatient(
            PatientCreateRequest request) {

        if (repository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistsException(
                    "Patient already exists");
        }

        Patient patient = mapper.toEntity(request);

        return mapper.toResponse(
                repository.save(patient));
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
}