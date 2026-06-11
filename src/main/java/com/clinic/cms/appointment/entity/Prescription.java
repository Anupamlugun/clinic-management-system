package com.clinic.cms.appointment.entity;

import com.clinic.cms.common.entity.BaseEntity;
import com.clinic.cms.doctor.entity.Doctor;
import com.clinic.cms.patient.entity.Patient;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "prescriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "appointment_id",
            nullable = false,
            unique = true
    )
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(length = 5000)
    private String diagnosis;

    @Column(length = 5000)
    private String notes;

    private LocalDate followUpDate;
}