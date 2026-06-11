package com.clinic.cms.appointment.repository;

import com.clinic.cms.appointment.entity.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentSlotRepository
        extends JpaRepository<AppointmentSlot, Long> {

    List<AppointmentSlot> findByDoctorId(Long doctorId);

    List<AppointmentSlot> findByDoctorIdAndSlotDate(
            Long doctorId,
            LocalDate slotDate
    );

    List<AppointmentSlot> findByDoctorIdAndSlotDateAndBookedFalse(
            Long doctorId,
            LocalDate slotDate
    );

    List<AppointmentSlot> findByDoctorIdAndSlotDateAndBookedFalseAndActiveTrue(
            Long doctorId,
            LocalDate slotDate
    );

    boolean existsByDoctorIdAndSlotDateAndStartTimeAndEndTime(
            Long doctorId,
            LocalDate slotDate,
            LocalTime startTime,
            LocalTime endTime
    );
}