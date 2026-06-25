package com.clinic.cms.doctor.repository;

import com.clinic.cms.doctor.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    boolean existsByEmail(String email);

    Optional<Doctor> findByEmail(String email);

    Page<Doctor> findByActive(Boolean active, Pageable pageable);Page<Doctor> findByActiveTrue(Pageable pageable);

    Page<Doctor> findByActiveFalse(Pageable pageable);

    List<Doctor> findTop5ByActiveTrueOrderByExperienceYearsDesc();
}
