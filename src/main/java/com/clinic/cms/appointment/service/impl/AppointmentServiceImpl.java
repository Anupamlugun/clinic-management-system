package com.clinic.cms.appointment.service.impl;

import com.clinic.cms.appointment.dto.v1.AppointmentCreateRequest;
import com.clinic.cms.appointment.dto.v1.AppointmentResponse;
import com.clinic.cms.appointment.dto.v1.AppointmentStatusUpdateRequest;
import com.clinic.cms.appointment.dto.v1.AppointmentUpdateRequest;
import com.clinic.cms.appointment.entity.Appointment;
import com.clinic.cms.appointment.entity.AppointmentSlot;
import com.clinic.cms.appointment.enums.AppointmentStatus;
import com.clinic.cms.appointment.mapper.v1.AppointmentMapper;
import com.clinic.cms.appointment.repository.AppointmentRepository;
import com.clinic.cms.appointment.repository.AppointmentSlotRepository;
import com.clinic.cms.appointment.service.AppointmentService;
import com.clinic.cms.appointment.workflow.AppointmentWorkflowRules;
import com.clinic.cms.billing.service.PaymentService;
import com.clinic.cms.doctor.entity.Doctor;
import com.clinic.cms.doctor.repository.DoctorRepository;
import com.clinic.cms.exception.custom.ResourceAlreadyExistsException;
import com.clinic.cms.exception.custom.ResourceNotFoundException;
import com.clinic.cms.patient.entity.Patient;
import com.clinic.cms.patient.repository.PatientRepository;
import jakarta.validation.ValidationException;
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
    private final PaymentService paymentService;
    private final AppointmentWorkflowRules appointmentWorkflowRules;

    @Override
    public AppointmentResponse createAppointment(
            AppointmentCreateRequest request) {

        validateFollowUpAppointment(request.followUp(),request.parentAppointmentId());


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

        AppointmentSlot slot =
                slotRepository.findByIdAndDoctorId(
                                request.slotId(),
                                request.doctorId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Slot not found for the selected doctor"));

        if (slot.getBooked()) {
            throw new ResourceAlreadyExistsException("Slot already booked");
        }

        slot.setBooked(true);

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

        Appointment savedAppointment = repository.save(appointment);
        paymentService.createPayment(savedAppointment.getId());
        return mapper.toResponse(savedAppointment);
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

        validateFollowUpAppointment(request.followUp(),request.parentAppointmentId());

        Appointment appointment =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Appointment not found"));

        AppointmentSlot slot =
                slotRepository.findByIdAndDoctorId(
                                request.slotId(),
                                appointment.getDoctor().getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Slot not found for the appointment doctor"));


        AppointmentSlot oldSlot = appointment.getSlot();

        if (slot.getBooked()
                && !slot.getId().equals(oldSlot.getId())) {
            throw new ResourceAlreadyExistsException("Slot already booked");
        }

        if (!slot.getId().equals(oldSlot.getId())) {
            oldSlot.setBooked(false);
            slot.setBooked(true);
        }


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

        appointmentWorkflowRules
                .validateTransition(
                        appointment.getStatus(),
                        request.status());

        appointment.setStatus(
                request.status());

        if (request.status() == AppointmentStatus.CANCELLED) {
            appointment.getSlot().setBooked(false);
        }

        return mapper.toResponse(
                repository.save(appointment));
    }

    private void validateFollowUpAppointment(
            Boolean followUp,
            Long parentAppointmentId) {

        // followUp = true => parentAppointmentId required
        if (Boolean.TRUE.equals(followUp)
                && parentAppointmentId == null) {

            throw new ValidationException(
                    "Parent appointment id is required for follow-up appointments");
        }

        // followUp = false/null => parentAppointmentId not allowed
        if (!Boolean.TRUE.equals(followUp)
                && parentAppointmentId != null) {

            throw new ValidationException(
                    "Parent appointment id can only be provided when followUp is true");
        }
    }
}