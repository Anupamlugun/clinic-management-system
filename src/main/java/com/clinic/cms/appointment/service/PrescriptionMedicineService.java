package com.clinic.cms.appointment.service;

import com.clinic.cms.appointment.dto.v1.PrescriptionMedicineCreateRequest;
import com.clinic.cms.appointment.dto.v1.PrescriptionMedicineResponse;
import com.clinic.cms.appointment.dto.v1.PrescriptionMedicineUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PrescriptionMedicineService {

    PrescriptionMedicineResponse createMedicine(
            PrescriptionMedicineCreateRequest request);

    PrescriptionMedicineResponse getMedicine(
            Long id);

    Page<PrescriptionMedicineResponse> getAllMedicines(
            Pageable pageable);

    PrescriptionMedicineResponse updateMedicine(
            Long id,
            PrescriptionMedicineUpdateRequest request);

    void deleteMedicine(
            Long id);

    Page<PrescriptionMedicineResponse> getMedicinesByPrescription(
            Long prescriptionId,
            Pageable pageable);
}