package com.clinic.cms.appointment.service.impl;

import com.clinic.cms.appointment.dto.v1.PrescriptionCreateRequest;
import com.clinic.cms.appointment.dto.v1.PrescriptionResponse;
import com.clinic.cms.appointment.dto.v1.PrescriptionUpdateRequest;
import com.clinic.cms.appointment.entity.Appointment;
import com.clinic.cms.appointment.entity.Prescription;
import com.clinic.cms.appointment.enums.AppointmentStatus;
import com.clinic.cms.appointment.mapper.v1.PrescriptionMapper;
import com.clinic.cms.appointment.repository.AppointmentRepository;
import com.clinic.cms.appointment.repository.PrescriptionMedicineRepository;
import com.clinic.cms.appointment.repository.PrescriptionRepository;
import com.clinic.cms.appointment.service.PrescriptionService;
import com.clinic.cms.auth.security.CurrentUserService;
import com.clinic.cms.doctor.entity.Doctor;
import com.clinic.cms.doctor.repository.DoctorRepository;
import com.clinic.cms.exception.custom.ResourceAlreadyExistsException;
import com.clinic.cms.exception.custom.ResourceNotFoundException;
import com.clinic.cms.exception.custom.ValidationException;
import com.clinic.cms.patient.entity.Patient;
import com.clinic.cms.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository repository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PrescriptionMedicineRepository medicineRepository;
    private final PrescriptionMapper mapper;
    private final CurrentUserService currentUserService;

    @Override
    public PrescriptionResponse createPrescription(
            PrescriptionCreateRequest request) {

        if (repository.existsByAppointmentId(request.appointmentId())) {
            throw new ResourceAlreadyExistsException(
                    "Prescription already exists for this appointment");
        }

        Appointment appointment = appointmentRepository
                .findById(request.appointmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found"));

        if (request.followUpDate() != null) {
            appointment.setStatus(AppointmentStatus.FOLLOW_UP_SCHEDULED);
        }

        Doctor doctor = doctorRepository
                .findById(request.doctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found"));

        Patient patient = patientRepository
                .findById(request.patientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found"));

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new ValidationException(
                    "Doctor does not belong to this appointment");
        }

        if (!appointment.getPatient().getId().equals(patient.getId())) {
            throw new ValidationException(
                    "Patient does not belong to this appointment");
        }

        Prescription prescription = mapper.toEntity(request);

        prescription.setAppointment(appointment);
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);

        return mapper.toResponse(
                repository.save(prescription));
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionResponse getPrescription(
            Long id) {

        Prescription prescription = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Prescription not found"));

        validateOwnership(prescription);

        return mapper.toResponse(prescription);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionResponse> getAllPrescriptions(
            Pageable pageable) {

        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public PrescriptionResponse updatePrescription(
            Long id,
            PrescriptionUpdateRequest request) {

        Prescription prescription = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Prescription not found"));

        validateOwnership(prescription);


        Appointment appointment = appointmentRepository
                .findById(prescription.getAppointment().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found"));

        if (request.followUpDate() != null) {
            appointment.setStatus(AppointmentStatus.FOLLOW_UP_SCHEDULED);
        }

        mapper.updateEntityFromRequest(
                request,
                prescription);

        return mapper.toResponse(
                repository.save(prescription));
    }

    @Override
    public void deletePrescription(
            Long id) {

        Prescription prescription = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Prescription not found"));

        validateOwnership(prescription);

        if (medicineRepository.existsByPrescriptionId(id)) {
            throw new ValidationException(
                    "Cannot delete prescription because medicines are attached");
        }

        repository.delete(prescription);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionResponse> getPrescriptionsByPatient(
            Long patientId,
            Pageable pageable) {

        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException(
                    "Patient not found");
        }

        return repository.findByPatientId(
                        patientId,
                        pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionResponse> getPrescriptionsByDoctor(
            Long doctorId,
            Pageable pageable) {

        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException(
                    "Doctor not found");
        }

        return repository.findByDoctorId(
                        doctorId,
                        pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionResponse getPrescriptionByAppointment(
            Long appointmentId) {

        Prescription prescription = repository.findByAppointmentId(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Prescription not found"));

        validateOwnership(prescription);

        return mapper.toResponse(prescription);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionResponse> getFollowUpPrescriptions(
            Pageable pageable) {

        return repository.findByFollowUpDateIsNotNull(
                        pageable)
                .map(mapper::toResponse);
    }

    private void validateOwnership(Prescription prescription) {

        if (currentUserService.hasRole("SYSTEM_ADMIN")) {
            return;
        }

        Long currentUserId = currentUserService.getUserId();

        if (currentUserService.hasRole("DOCTOR")) {

            Long doctorUserId = prescription.getDoctor()
                    .getUser()
                    .getId();

            if (!doctorUserId.equals(currentUserId)) {
                throw new AccessDeniedException(
                        "You can only access your own prescriptions");
            }

            return;
        }

        if (currentUserService.hasRole("PATIENT")) {

            Long patientUserId = prescription.getPatient()
                    .getUser()
                    .getId();

            if (!patientUserId.equals(currentUserId)) {
                throw new AccessDeniedException(
                        "You can only access your own prescriptions");
            }

            return;
        }

        throw new AccessDeniedException("Access denied");
    }
}