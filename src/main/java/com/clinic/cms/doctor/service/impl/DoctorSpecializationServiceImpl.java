package com.clinic.cms.doctor.service.impl;

import com.clinic.cms.doctor.dto.*;
import com.clinic.cms.doctor.entity.DoctorSpecialization;
import com.clinic.cms.doctor.mapper.DoctorSpecializationMapper;
import com.clinic.cms.doctor.repository.DoctorSpecializationRepository;
import com.clinic.cms.doctor.service.DoctorSpecializationService;
import com.clinic.cms.exception.custom.ResourceAlreadyExistsException;
import com.clinic.cms.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorSpecializationServiceImpl
        implements DoctorSpecializationService {

    private final DoctorSpecializationRepository repository;
    private final DoctorSpecializationMapper mapper;

    @Override
    public DoctorSpecializationResponse createSpecialization(
            DoctorSpecializationCreateRequest request) {

        if (repository.existsByCode(request.code())) {
            throw new ResourceAlreadyExistsException(
                    "Specialization code already exists");
        }

        DoctorSpecialization specialization =
                mapper.toEntity(request);

        return mapper.toResponse(
                repository.save(specialization));
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorSpecializationResponse getSpecialization(
            Long id) {

        DoctorSpecialization specialization =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Specialization not found"));

        return mapper.toResponse(specialization);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorSpecializationResponse> getAllSpecializations(
            Pageable pageable) {

        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public DoctorSpecializationResponse updateSpecialization(
            Long id,
            DoctorSpecializationUpdateRequest request) {

        DoctorSpecialization specialization =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Specialization not found"));

        if (request.code() != null &&
                !request.code().equals(specialization.getCode())) {

            if (repository.existsByCode(request.code())) {
                throw new ResourceAlreadyExistsException(
                        "Specialization code already exists");
            }

            specialization.setCode(request.code());
        }

        specialization.setName(request.name());
        specialization.setActive(request.active());

        return mapper.toResponse(
                repository.save(specialization));
    }

    @Override
    public void deleteSpecialization(Long id) {

        DoctorSpecialization specialization =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Specialization not found"));

        repository.delete(specialization);
    }
}