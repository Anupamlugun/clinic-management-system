package com.clinic.cms.doctor.service.impl;

import com.clinic.cms.doctor.dto.DoctorCreateRequest;
import com.clinic.cms.doctor.dto.DoctorResponse;
import com.clinic.cms.doctor.dto.DoctorUpdateRequest;
import com.clinic.cms.doctor.entity.Doctor;
import com.clinic.cms.doctor.mapper.DoctorMapper;
import com.clinic.cms.doctor.repository.DoctorRepository;
import com.clinic.cms.doctor.service.DoctorService;
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
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository repository;
    private final DoctorMapper mapper;

    @Override
    public DoctorResponse createDoctor(
            DoctorCreateRequest request) {

        if(repository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException(
                    "Doctor already exists");
        }

        Doctor doctor = mapper.toEntity(request);
        doctor.setActive(true);

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

        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setExperienceYears(request.getExperienceYears());

        return mapper.toResponse(
                repository.save(doctor));
    }

    @Override
    public void deleteDoctor(Long id) {

        Doctor doctor = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found"));

        repository.delete(doctor);
    }
}
