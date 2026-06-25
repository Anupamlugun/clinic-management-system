package com.clinic.cms.appointment.service.impl;

import com.clinic.cms.appointment.dto.v1.AppointmentSlotResponse;
import com.clinic.cms.appointment.entity.AppointmentSlot;
import com.clinic.cms.appointment.mapper.v1.AppointmentSlotMapper;
import com.clinic.cms.appointment.repository.AppointmentSlotRepository;
import com.clinic.cms.appointment.service.AppointmentSlotService;
import com.clinic.cms.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentSlotServiceImpl
        implements AppointmentSlotService {

    private final AppointmentSlotRepository repository;
    private final AppointmentSlotMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public AppointmentSlotResponse getSlot(Long id) {

        AppointmentSlot slot = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment slot not found"));

        return mapper.toResponse(slot);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentSlotResponse> getAllSlots(
            Pageable pageable) {

        return repository.findByActiveTrue(pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentSlotResponse> getSlotsByDoctorAndDate(
            Long doctorId,
            LocalDate slotDate,
            Pageable pageable) {

        return repository
                .findByDoctorIdAndSlotDateAndActiveTrue(
                        doctorId,
                        slotDate,
                        pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentSlotResponse> getAvailableSlots(Pageable pageable) {

        return repository.findByBookedFalseAndActiveTrue(pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentSlotResponse> getBookedSlots(Pageable pageable) {

        return repository.findByBookedTrueAndActiveTrue(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public AppointmentSlotResponse activateSlot(Long id) {

        AppointmentSlot slot = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment slot not found"));

        slot.setActive(true);

        return mapper.toResponse(repository.save(slot));
    }

    @Override
    public AppointmentSlotResponse deactivateSlot(Long id) {

        AppointmentSlot slot = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment slot not found"));

        slot.setActive(false);

        return mapper.toResponse(repository.save(slot));
    }
}