package com.clinic.cms.doctor.repository;

import com.clinic.cms.doctor.entity.DoctorSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorSpecializationRepository
        extends JpaRepository<DoctorSpecialization, Long> {

    Optional<DoctorSpecialization> findByCode(String code);

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);
}