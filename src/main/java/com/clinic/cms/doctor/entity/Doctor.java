package com.clinic.cms.doctor.entity;

import com.clinic.cms.auth.entity.User;
import com.clinic.cms.common.entity.BaseEntity;
import com.clinic.cms.doctor.enums.DoctorStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "doctors",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_doctor_email",
                        columnNames = "email"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            unique = true,
            foreignKey = @ForeignKey(name = "fk_doctor_user")
    )
    private User user;

    @Column(nullable = false,length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(length = 20)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "specialization_id",
            nullable = false
    )
    private DoctorSpecialization specialization;

    @Column(nullable = false)
    private Integer experienceYears;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal consultationFee;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal followUpFee;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false, length = 20)
    private DoctorStatus status = DoctorStatus.ACTIVE;
}
