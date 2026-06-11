package com.clinic.cms.patient.repository;

import com.clinic.cms.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository
        extends JpaRepository<Patient, Long> {

    boolean existsByEmail(String email);
}