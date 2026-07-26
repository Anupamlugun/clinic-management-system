package com.clinic.cms.appointment.controller.v1;

import com.clinic.cms.appointment.dto.v1.PrescriptionMedicineCreateRequest;
import com.clinic.cms.appointment.dto.v1.PrescriptionMedicineResponse;
import com.clinic.cms.appointment.dto.v1.PrescriptionMedicineUpdateRequest;
import com.clinic.cms.appointment.service.PrescriptionMedicineService;
import com.clinic.cms.common.dto.v1.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/prescription-medicines")
@RequiredArgsConstructor
@Tag(
        name = "Prescription Medicine",
        description = "Prescription Medicine APIs")
public class PrescriptionMedicineController {

    private final PrescriptionMedicineService prescriptionMedicineService;

    @PostMapping
    @PreAuthorize("hasRole('DOCTOR') and hasAuthority('CREATE_PRESCRIPTION_MEDICINE')")
    @Operation(summary = "Add Medicine To Prescription")
    public ResponseEntity<ApiResponse<PrescriptionMedicineResponse>> create(
            @Valid
            @RequestBody
            PrescriptionMedicineCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Medicine added successfully",
                        prescriptionMedicineService.createMedicine(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_PRESCRIPTION_MEDICINE')")
    @Operation(summary = "Get Prescription Medicine By Id")
    public ResponseEntity<ApiResponse<PrescriptionMedicineResponse>> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Medicine fetched successfully",
                        prescriptionMedicineService.getMedicine(id)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_ALL_PRESCRIPTION_MEDICINES')")
    @Operation(summary = "Get All Prescription Medicines")
    public ResponseEntity<ApiResponse<Page<PrescriptionMedicineResponse>>> getAll(
            @ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Medicines fetched successfully",
                        prescriptionMedicineService.getAllMedicines(pageable)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR') and hasAuthority('UPDATE_PRESCRIPTION_MEDICINE')")
    @Operation(summary = "Update Prescription Medicine")
    public ResponseEntity<ApiResponse<PrescriptionMedicineResponse>> update(
            @PathVariable Long id,
            @Valid
            @RequestBody
            PrescriptionMedicineUpdateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Medicine updated successfully",
                        prescriptionMedicineService.updateMedicine(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR') and hasAuthority('DELETE_PRESCRIPTION_MEDICINE')")
    @Operation(summary = "Delete Prescription Medicine")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        prescriptionMedicineService.deleteMedicine(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Medicine deleted successfully"));
    }

    @GetMapping("/by-prescription/{prescriptionId}")
    @PreAuthorize("hasAuthority('VIEW_PRESCRIPTION_MEDICINE')")
    @Operation(summary = "Get Medicines By Prescription")
    public ResponseEntity<ApiResponse<Page<PrescriptionMedicineResponse>>> getByPrescription(
            @PathVariable Long prescriptionId,
            @ParameterObject
            @PageableDefault(size = 10, sort = "medicineName", direction = Sort.Direction.ASC)
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Medicines fetched successfully",
                        prescriptionMedicineService.getMedicinesByPrescription(
                                prescriptionId,
                                pageable)));
    }
}