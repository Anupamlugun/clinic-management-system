package com.clinic.cms.doctor.controller.v1;

import com.clinic.cms.common.dto.v1.ApiResponse;
import com.clinic.cms.doctor.dto.v1.DoctorSpecializationCreateRequest;
import com.clinic.cms.doctor.dto.v1.DoctorSpecializationResponse;
import com.clinic.cms.doctor.dto.v1.DoctorSpecializationUpdateRequest;
import com.clinic.cms.doctor.service.DoctorSpecializationService;
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
@RequiredArgsConstructor
@RequestMapping("/v1/doctor-specializations")
@Tag(
        name = "Doctor Specialization Management",
        description = "APIs for managing doctor specializations such as Cardiology, Neurology, Orthopedics, Pediatrics, and other medical specialties."
)
public class DoctorSpecializationController {

    private final DoctorSpecializationService service;

    @PostMapping
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('CREATE_DOCTOR_SPECIALIZATION')")
    @Operation(summary = "Create Doctor specializations")
    public ResponseEntity<ApiResponse<DoctorSpecializationResponse>> create(
            @Valid @RequestBody DoctorSpecializationCreateRequest request) {

        DoctorSpecializationResponse response =
                service.createSpecialization(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Doctor specialization created successfully",
                        response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_DOCTOR_SPECIALIZATION')")
    @Operation(summary = "Get Doctor specializations By Id")
    public ResponseEntity<ApiResponse<DoctorSpecializationResponse>> get(
            @PathVariable Long id) {


        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor specialization fetched successfully",
                        service.getSpecialization(id)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_ALL_DOCTOR_SPECIALIZATIONS')")
    @Operation(summary="Get All Doctor Specialization")
    public ResponseEntity<ApiResponse<Page<DoctorSpecializationResponse>>> getAll(
            @ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            )
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor specialization fetched successfully",
                        service.getAllSpecializations(pageable)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('UPDATE_DOCTOR_SPECIALIZATION')")
    @Operation(summary="Update Doctor Specialization")
    public ResponseEntity<ApiResponse<DoctorSpecializationResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody DoctorSpecializationUpdateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor specialization updated successfully",
                        service.updateSpecialization(id, request)));

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('DELETE_DOCTOR_SPECIALIZATION')")
    @Operation(summary="Delete Doctor Specialization")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        service.deleteSpecialization(id);

        return ResponseEntity.ok(
                ApiResponse.success("Doctor specialization deleted successfully"));
    }
}