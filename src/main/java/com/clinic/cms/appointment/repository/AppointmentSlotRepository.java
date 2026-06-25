package com.clinic.cms.appointment.repository;

import com.clinic.cms.appointment.entity.AppointmentSlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentSlotRepository
        extends JpaRepository<AppointmentSlot, Long> {

    boolean existsByDoctorIdAndSlotDate(Long doctorId, LocalDate slotDate);

    Page<AppointmentSlot> findByActiveTrue(
            Pageable pageable);

    Page<AppointmentSlot> findByDoctorIdAndSlotDateAndActiveTrue(
            Long doctorId,
            LocalDate slotDate,
            Pageable pageable);

    Optional<AppointmentSlot> findByIdAndDoctorId(
            Long slotId,
            Long doctorId);

    Page<AppointmentSlot> findByBookedFalseAndActiveTrue(Pageable pageable);

    Page<AppointmentSlot> findByBookedTrueAndActiveTrue(Pageable pageable);
}