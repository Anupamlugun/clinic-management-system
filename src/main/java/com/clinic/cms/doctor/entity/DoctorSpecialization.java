package com.clinic.cms.doctor.entity;

import com.clinic.cms.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@DynamicUpdate
@Table(
        name = "doctor_specializations",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_doctor_specialization_code",
                        columnNames = "code"
                )
        }
)
public class DoctorSpecialization extends BaseEntity {

    @NotBlank
    @Size(max = 50)
    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "active", nullable = false)
    private Boolean active = true;
}