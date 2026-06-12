package com.clinic.cms.patient.entity;

import com.clinic.cms.auth.entity.User;
import com.clinic.cms.common.entity.BaseEntity;
import com.clinic.cms.common.enums.Gender;
import com.clinic.cms.patient.enums.BloodGroup;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "patients",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_patient_email",
                        columnNames = "email"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            unique = true,
            foreignKey = @ForeignKey(name = "fk_patient_user")
    )
    private User user;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(length = 20)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group", length = 15)
    private BloodGroup bloodGroup;

    @Column(length = 100)
    private String emergencyContactName;

    @Column(length = 20)
    private String emergencyContactNumber;

    @Column(length = 500)
    private String address;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;
}