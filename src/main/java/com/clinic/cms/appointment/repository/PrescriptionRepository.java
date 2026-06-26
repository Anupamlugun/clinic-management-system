package com.clinic.cms.appointment.repository;

import com.clinic.cms.appointment.entity.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    Page<Prescription> findByPatientId(
            Long patientId,
            Pageable pageable);

    Page<Prescription> findByDoctorId(
            Long doctorId,
            Pageable pageable);

    Optional<Prescription> findByAppointmentId(
            Long appointmentId);

    Page<Prescription> findByFollowUpDateIsNotNull(
            Pageable pageable);

    Page<Prescription> findByFollowUpDate(
            LocalDate followUpDate,
            Pageable pageable);

    boolean existsByAppointmentId(Long appointmentId);
}