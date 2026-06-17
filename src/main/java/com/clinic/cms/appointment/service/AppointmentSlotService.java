package com.clinic.cms.appointment.service;

import com.clinic.cms.appointment.dto.v1.AppointmentSlotResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface AppointmentSlotService {

    AppointmentSlotResponse getSlot(Long id);

    Page<AppointmentSlotResponse> getAllSlots(
            Pageable pageable);

    Page<AppointmentSlotResponse> getSlotsByDoctorAndDate(
            Long doctorId,
            LocalDate slotDate,
            Pageable pageable);
}