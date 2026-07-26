package com.clinic.cms.doctor.controller.v1;

import com.clinic.cms.common.dto.v1.ApiResponse;
import com.clinic.cms.common.dto.v1.EnumResponse;
import com.clinic.cms.doctor.dto.v1.*;
import com.clinic.cms.doctor.enums.DoctorStatus;
import com.clinic.cms.doctor.service.DoctorService;
import com.clinic.cms.patient.dto.v1.PatientResponse;
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

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctor", description = "Doctor Management APIs")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('CREATE_DOCTOR')")
    @Operation(summary = "Create Doctor")
    public ResponseEntity<ApiResponse<DoctorResponse>> create(
            @Valid @RequestBody DoctorCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Doctor created successfully",
                        doctorService.createDoctor(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("""
    (hasRole('SYSTEM_ADMIN') or
     hasRole('RECEPTIONIST') or
     hasRole('DOCTOR'))
     and hasAuthority('VIEW_DOCTOR')
    """)
    @Operation(summary = "Get Doctor By Id")
    public ResponseEntity<ApiResponse<DoctorResponse>> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor fetch successfully",
                        doctorService.getDoctor(id)));
    }

    @GetMapping
    @PreAuthorize("""
    (hasRole('SYSTEM_ADMIN') or
     hasRole('RECEPTIONIST'))
     and hasAuthority('VIEW_ALL_DOCTORS')
    """)
    @Operation(summary = "Get All Doctors")
    public ResponseEntity<ApiResponse<Page<DoctorResponse>>> getAll(
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
                        "Doctor fetch successfully",
                        doctorService.getAllDoctors(pageable)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('UPDATE_DOCTOR')")
    @Operation(summary = "Update Doctor")
    public ResponseEntity<ApiResponse<DoctorResponse>> update(
            @PathVariable Long id,
            @RequestBody DoctorUpdateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor updated successfully",
                        doctorService.updateDoctor(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('DELETE_DOCTOR')")
    @Operation(summary = "Delete Doctor")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        doctorService.deleteDoctor(id);

        return ResponseEntity.ok(
                ApiResponse.success("Doctor deleted successfully"));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('UPDATE_DOCTOR_STATUS')")
    @Operation(summary = "update doctor status")
    public ResponseEntity<ApiResponse<DoctorResponse>> updateStatus(@PathVariable Long id, DoctorStatusUpdateRequest request) {

        return ResponseEntity.ok(ApiResponse.success("Doctor status updated successfully", doctorService.updateDoctorStatus(id, request)));

    }

    @GetMapping("/status")
    @PreAuthorize("""
    (hasRole('SYSTEM_ADMIN') or
     hasRole('RECEPTIONIST') or
     hasRole('DOCTOR'))
     and hasAuthority('VIEW_DOCTOR')
    """)
    @Operation(summary = "Fetch All Doctor Status")
    public ResponseEntity<ApiResponse<List<EnumResponse>>> getDoctorStatus(){
        List<EnumResponse> doctorStatus = Arrays.stream(DoctorStatus.values())
                .map(ds -> new EnumResponse(
                        ds.name(),
                        ds.getDisplayName()))
                .toList();

        return ResponseEntity.ok(ApiResponse.success(
                "Doctor status fetched successfully",
                doctorStatus));
    }
    @GetMapping("/top")
    @PreAuthorize("""
    (hasRole('SYSTEM_ADMIN') or
     hasRole('RECEPTIONIST'))
     and hasAuthority('VIEW_ALL_DOCTORS')
    """)
    @Operation(summary = "Get Top Doctors")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getTopDoctors() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Top doctors fetched successfully",
                        doctorService.getTopDoctors()));
    }

    @GetMapping("/active")
    @PreAuthorize("""
    (hasRole('SYSTEM_ADMIN') or
     hasRole('RECEPTIONIST'))
     and hasAuthority('VIEW_ALL_DOCTORS')
    """)
    @Operation(summary = "Get Active Doctors")
    public ResponseEntity<ApiResponse<Page<DoctorResponse>>> getActiveDoctors(
            @ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC)
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Active doctors fetched successfully",
                        doctorService.getActiveDoctors(pageable)));
    }

    @GetMapping("/inactive")
    @PreAuthorize("""
    hasRole('SYSTEM_ADMIN')
    and hasAuthority('VIEW_ALL_DOCTORS')
    """)
    @Operation(summary = "Get Inactive Doctors")
    public ResponseEntity<ApiResponse<Page<DoctorResponse>>> getInactiveDoctors(
            @ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC)
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Inactive doctors fetched successfully",
                        doctorService.getInactiveDoctors(pageable)));
    }

    @GetMapping("/{id}/patients")
    @PreAuthorize("""
    hasRole('SYSTEM_ADMIN')
    and hasAuthority('VIEW_PATIENT')
    """)
    @Operation(summary = "Get Doctor Patients")
    public ResponseEntity<ApiResponse<List<PatientResponse>>> getDoctorPatients(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor patients fetched successfully",
                        doctorService.getDoctorPatients(id)));
    }

    @GetMapping("/{id}/statistics")
    @PreAuthorize("""
    hasRole('SYSTEM_ADMIN')
    and hasAuthority('VIEW_DOCTOR')
    """)
    @Operation(summary = "Get Doctor Statistics")
    public ResponseEntity<ApiResponse<DoctorStatisticsResponse>> getDoctorStatistics(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor statistics fetched successfully",
                        doctorService.getDoctorStatistics(id)));
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('ACTIVATE_DOCTOR')")
    @Operation(summary = "Activate Doctor")
    public ResponseEntity<ApiResponse<DoctorResponse>> activateDoctor(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor activated successfully",
                        doctorService.activateDoctor(id)));
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('DEACTIVATE_DOCTOR')")
    @Operation(summary = "Deactivate Doctor")
    public ResponseEntity<ApiResponse<DoctorResponse>> deactivateDoctor(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor deactivated successfully",
                        doctorService.deactivateDoctor(id)));
    }
}
