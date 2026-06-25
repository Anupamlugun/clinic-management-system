package com.clinic.cms.appointment.repository;

import com.clinic.cms.appointment.entity.Appointment;
import com.clinic.cms.appointment.enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByAppointmentDate(LocalDate appointmentDate);

    List<Appointment> findByStatus(AppointmentStatus status);

    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);

    Page<Appointment> findByDoctorId(Long doctorId, Pageable pageable);

    Page<Appointment> findByStatus(AppointmentStatus status, Pageable pageable);

    Page<Appointment> findByAppointmentDate(LocalDate date, Pageable pageable);

    Page<Appointment> findByAppointmentDateGreaterThanEqual(
            LocalDate date,
            Pageable pageable);

    Page<Appointment> findByPatientIdAndAppointmentDateLessThan(
            Long patientId,
            LocalDate date,
            Pageable pageable);
}