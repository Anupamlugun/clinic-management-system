package com.clinic.cms.doctor.entity;

import com.clinic.cms.common.entity.BaseEntity;
import com.clinic.cms.common.enums.WeekDay;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(
        name = "doctor_schedules",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_doctor_day",
                        columnNames = {"doctor_id", "day"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorSchedule extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "doctor_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_schedule_doctor")
    )
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private WeekDay day;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;
}
