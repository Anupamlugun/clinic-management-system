package com.clinic.cms.appointment.service.impl;

import com.clinic.cms.appointment.dto.v1.PrescriptionMedicineCreateRequest;
import com.clinic.cms.appointment.dto.v1.PrescriptionMedicineResponse;
import com.clinic.cms.appointment.dto.v1.PrescriptionMedicineUpdateRequest;
import com.clinic.cms.appointment.entity.Prescription;
import com.clinic.cms.appointment.entity.PrescriptionMedicine;
import com.clinic.cms.appointment.mapper.v1.PrescriptionMedicineMapper;
import com.clinic.cms.appointment.repository.PrescriptionMedicineRepository;
import com.clinic.cms.appointment.repository.PrescriptionRepository;
import com.clinic.cms.appointment.service.PrescriptionMedicineService;
import com.clinic.cms.exception.custom.ResourceNotFoundException;
import com.clinic.cms.exception.custom.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionMedicineServiceImpl
        implements PrescriptionMedicineService {

    private final PrescriptionMedicineRepository repository;
    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMedicineMapper mapper;

    @Override
    public PrescriptionMedicineResponse createMedicine(
            PrescriptionMedicineCreateRequest request) {

        Prescription prescription = prescriptionRepository
                .findById(request.prescriptionId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Prescription not found"));

        validateDuration(request.durationDays());

        PrescriptionMedicine medicine =
                mapper.toEntity(request);

        medicine.setPrescription(prescription);

        return mapper.toResponse(
                repository.save(medicine));
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionMedicineResponse getMedicine(
            Long id) {

        return mapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Medicine not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionMedicineResponse> getAllMedicines(
            Pageable pageable) {

        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public PrescriptionMedicineResponse updateMedicine(
            Long id,
            PrescriptionMedicineUpdateRequest request) {

        PrescriptionMedicine medicine =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Medicine not found"));

        if (request.durationDays() != null) {
            validateDuration(request.durationDays());
        }

        mapper.updateEntityFromRequest(
                request,
                medicine);

        return mapper.toResponse(
                repository.save(medicine));
    }

    @Override
    public void deleteMedicine(
            Long id) {

        PrescriptionMedicine medicine =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Medicine not found"));

        repository.delete(medicine);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionMedicineResponse> getMedicinesByPrescription(
            Long prescriptionId,
            Pageable pageable) {

        if (!prescriptionRepository.existsById(prescriptionId)) {
            throw new ResourceNotFoundException(
                    "Prescription not found");
        }

        return repository.findByPrescriptionId(
                        prescriptionId,
                        pageable)
                .map(mapper::toResponse);
    }

    private void validateDuration(
            Integer durationDays) {

        if (durationDays == null) {
            throw new ValidationException(
                    "Duration days is required");
        }

        if (durationDays <= 0) {
            throw new ValidationException(
                    "Duration days must be greater than zero");
        }
    }
}