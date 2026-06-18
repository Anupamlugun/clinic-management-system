package com.clinic.cms.appointment.entity;

import com.clinic.cms.appointment.enums.AppointmentStatus;
import com.clinic.cms.common.entity.BaseEntity;
import com.clinic.cms.doctor.entity.Doctor;
import com.clinic.cms.patient.entity.Patient;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "slot_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_appointment_slot"))
    private AppointmentSlot slot;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AppointmentStatus status;

    @Column(length = 1000)
    private String reason;

    @Column(nullable = false)
    @Builder.Default
    private Boolean followUp = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_appointment_id")
    private Appointment parentAppointment;
}
