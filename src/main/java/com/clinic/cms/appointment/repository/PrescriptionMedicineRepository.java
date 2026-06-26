package com.clinic.cms.appointment.repository;

import com.clinic.cms.appointment.entity.PrescriptionMedicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionMedicineRepository
        extends JpaRepository<PrescriptionMedicine, Long> {

    Page<PrescriptionMedicine> findByPrescriptionId(
            Long prescriptionId,
            Pageable pageable);

    boolean existsByPrescriptionId(
            Long prescriptionId);
}
