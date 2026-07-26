package com.clinic.cms.patient.controller.v1;

import com.clinic.cms.appointment.dto.v1.AppointmentResponse;
import com.clinic.cms.billing.dto.v1.PaymentResponse;
import com.clinic.cms.common.dto.v1.ApiResponse;
import com.clinic.cms.common.dto.v1.EnumResponse;
import com.clinic.cms.patient.dto.v1.PatientCreateRequest;
import com.clinic.cms.patient.dto.v1.PatientResponse;
import com.clinic.cms.patient.dto.v1.PatientUpdateRequest;
import com.clinic.cms.patient.enums.BloodGroup;
import com.clinic.cms.patient.service.PatientService;
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
@RequestMapping("/v1/patients")
@RequiredArgsConstructor
@Tag(name = "Patient", description="Patient Management API")
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','RECEPTIONIST') and hasAuthority('CREATE_PATIENT')")
    @Operation(summary = "Create Patient")
    public ResponseEntity<ApiResponse<PatientResponse>>
    create(
            @Valid @RequestBody
            PatientCreateRequest request) {

        return ResponseEntity.status(
                        HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Patient created successfully",
                        patientService.createPatient(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_PATIENT')")
    @Operation(summary = "Fetch Patient By Id")
    public ResponseEntity<ApiResponse<PatientResponse>>
    get(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient fetched successfully",
                        patientService.getPatient(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','RECEPTIONIST') and hasAuthority('VIEW_ALL_PATIENTS')")
    @Operation(summary = "Fetch All Patient")
    public ResponseEntity<ApiResponse<Page<PatientResponse>>>
    getAll(
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
                        "Patients fetched successfully",
                        patientService.getAllPatients(pageable)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','RECEPTIONIST') and hasAuthority('UPDATE_PATIENT')")
    @Operation(summary = "Update Patient")
    public ResponseEntity<ApiResponse<PatientResponse>>
    update(
            @PathVariable Long id,
            @RequestBody PatientUpdateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient updated successfully",
                        patientService.updatePatient(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('DELETE_PATIENT')")
    @Operation(summary = "Delete Patient")
    public ResponseEntity<ApiResponse<Void>>
    delete(@PathVariable Long id) {

        patientService.deletePatient(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient deleted successfully"));
    }

    @GetMapping("/blood-groups")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Fetch All Blood Groups")
    public ResponseEntity<ApiResponse<List<EnumResponse>>> getBloodGroups() {

        List<EnumResponse> bloodGroups = Arrays.stream(BloodGroup.values())
                .map(bg -> new EnumResponse(
                        bg.name(),
                        bg.getDisplayName()))
                .toList();

        return ResponseEntity.ok(ApiResponse.success(
                "Blood groups fetched successfully",
                bloodGroups));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','RECEPTIONIST') and hasAuthority('VIEW_ALL_PATIENTS')")
    @Operation(summary = "Search Patients")
    public ResponseEntity<ApiResponse<Page<PatientResponse>>> search(
            @RequestParam(required = false) String keyword,
            @ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patients fetched successfully",
                        patientService.searchPatients(keyword, pageable)));
    }

    @GetMapping("/{id}/payments")
    @PreAuthorize("hasAuthority('VIEW_PAYMENT')")
    @Operation(summary = "Fetch Patient Payments")
    public ResponseEntity<ApiResponse<Page<PaymentResponse>>> getPayments(
            @PathVariable Long id,
            @ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient payments fetched successfully",
                        patientService.getPatientPayments(id, pageable)));
    }

    @GetMapping("/{id}/appointments")
    @PreAuthorize("hasAuthority('VIEW_APPOINTMENT')")
    @Operation(summary = "Fetch Patient Appointments")
    public ResponseEntity<ApiResponse<Page<AppointmentResponse>>> getAppointments(
            @PathVariable Long id,
            @ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "appointmentDate",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient appointments fetched successfully",
                        patientService.getPatientAppointments(id, pageable)));
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('ACTIVATE_PATIENT')")
    @Operation(summary = "Activate Patient")
    public ResponseEntity<ApiResponse<PatientResponse>> activate(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient activated successfully",
                        patientService.activatePatient(id)));
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('SYSTEM_ADMIN') and hasAuthority('DEACTIVATE_PATIENT')")
    @Operation(summary = "Deactivate Patient")
    public ResponseEntity<ApiResponse<PatientResponse>> deactivate(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient deactivated successfully",
                        patientService.deactivatePatient(id)));
    }
}