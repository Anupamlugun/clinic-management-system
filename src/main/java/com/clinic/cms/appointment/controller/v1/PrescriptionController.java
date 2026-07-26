package com.clinic.cms.appointment.controller.v1;

import com.clinic.cms.appointment.dto.v1.PrescriptionCreateRequest;
import com.clinic.cms.appointment.dto.v1.PrescriptionResponse;
import com.clinic.cms.appointment.dto.v1.PrescriptionUpdateRequest;
import com.clinic.cms.appointment.service.PrescriptionService;
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
@RequestMapping("/v1/prescriptions")
@RequiredArgsConstructor
@Tag(
        name = "Prescription",
        description = "Prescription Management APIs")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping
    @PreAuthorize("""
        (hasRole('SYSTEM_ADMIN') or hasRole('DOCTOR'))
        and hasAuthority('CREATE_PRESCRIPTION')
        """)
    @Operation(summary = "Create Prescription")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> create(
            @Valid
            @RequestBody
            PrescriptionCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Prescription created successfully",
                        prescriptionService.createPrescription(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_PRESCRIPTION')")
    @Operation(summary = "Get Prescription By Id")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Prescription fetched successfully",
                        prescriptionService.getPrescription(id)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_ALL_PRESCRIPTIONS')")
    @Operation(summary = "Get All Prescriptions")
    public ResponseEntity<ApiResponse<Page<PrescriptionResponse>>> getAll(
            @ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Prescriptions fetched successfully",
                        prescriptionService.getAllPrescriptions(pageable)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("""
        (hasRole('SYSTEM_ADMIN') or hasRole('DOCTOR'))
        and hasAuthority('UPDATE_PRESCRIPTION')
        """)
    @Operation(summary = "Update Prescription")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> update(
            @PathVariable Long id,
            @Valid
            @RequestBody
            PrescriptionUpdateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Prescription updated successfully",
                        prescriptionService.updatePrescription(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("""
        hasRole('SYSTEM_ADMIN')
        and hasAuthority('DELETE_PRESCRIPTION')
        """)
    @Operation(summary = "Delete Prescription")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        prescriptionService.deletePrescription(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Prescription deleted successfully"));
    }

    @GetMapping("/by-patient/{patientId}")
    @PreAuthorize("hasAuthority('VIEW_PRESCRIPTION')")
    @Operation(summary = "Get Prescriptions By Patient")
    public ResponseEntity<ApiResponse<Page<PrescriptionResponse>>> getByPatient(
            @PathVariable Long patientId,
            @ParameterObject
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Prescriptions fetched successfully",
                        prescriptionService.getPrescriptionsByPatient(patientId, pageable)));
    }

    @GetMapping("/by-doctor/{doctorId}")
    @PreAuthorize("hasAuthority('VIEW_PRESCRIPTION')")
    @Operation(summary = "Get Prescriptions By Doctor")
    public ResponseEntity<ApiResponse<Page<PrescriptionResponse>>> getByDoctor(
            @PathVariable Long doctorId,
            @ParameterObject
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Prescriptions fetched successfully",
                        prescriptionService.getPrescriptionsByDoctor(doctorId, pageable)));
    }

    @GetMapping("/by-appointment/{appointmentId}")
    @PreAuthorize("hasAuthority('VIEW_PRESCRIPTION')")
    @Operation(summary = "Get Prescription By Appointment")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> getByAppointment(
            @PathVariable Long appointmentId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Prescription fetched successfully",
                        prescriptionService.getPrescriptionByAppointment(appointmentId)));
    }

    @GetMapping("/follow-up")
    @PreAuthorize("hasAuthority('VIEW_PRESCRIPTION')")
    @Operation(summary = "Get Prescriptions With Follow-up")
    public ResponseEntity<ApiResponse<Page<PrescriptionResponse>>> getFollowUps(
            @ParameterObject
            @PageableDefault(size = 10, sort = "followUpDate")
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Follow-up prescriptions fetched successfully",
                        prescriptionService.getFollowUpPrescriptions(pageable)));
    }
}