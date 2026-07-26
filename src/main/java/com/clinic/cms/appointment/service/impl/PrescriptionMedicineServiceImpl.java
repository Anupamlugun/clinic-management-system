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
import com.clinic.cms.auth.security.CurrentUserService;
import com.clinic.cms.exception.custom.ResourceNotFoundException;
import com.clinic.cms.exception.custom.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
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
    private final CurrentUserService currentUserService;

    @Override
    public PrescriptionMedicineResponse createMedicine(
            PrescriptionMedicineCreateRequest request) {

        Prescription prescription = prescriptionRepository
                .findById(request.prescriptionId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Prescription not found"));

        checkPrescriptionAccess(prescription);

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

        PrescriptionMedicine medicine = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Medicine not found"));

        checkPrescriptionAccess(medicine.getPrescription());

        return mapper.toResponse(medicine);
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

        PrescriptionMedicine medicine = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Medicine not found"));

        checkPrescriptionAccess(medicine.getPrescription());

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

        PrescriptionMedicine medicine = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Medicine not found"));

        checkPrescriptionAccess(medicine.getPrescription());

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

    private void checkPrescriptionAccess(Prescription prescription) {

        // System Admin has unrestricted access
        if (currentUserService.hasRole("SYSTEM_ADMIN")) {
            return;
        }

        // Only doctors should reach here because of @PreAuthorize,
        // but this is an extra safety check.
        if (!currentUserService.hasRole("DOCTOR")) {
            throw new AccessDeniedException("Access denied.");
        }

        Long loggedInUserId = currentUserService.getUserId();

        Long prescriptionDoctorUserId = prescription
                .getAppointment()
                .getDoctor()
                .getUser()
                .getId();

        if (!loggedInUserId.equals(prescriptionDoctorUserId)) {
            throw new AccessDeniedException(
                    "You are not allowed to access this prescription.");
        }
    }
}