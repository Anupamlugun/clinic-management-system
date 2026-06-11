package com.clinic.cms.patient.controller.v1;

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
    @Operation(summary = "Fetch Patient By Id")
    public ResponseEntity<ApiResponse<PatientResponse>>
    get(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient fetched successfully",
                        patientService.getPatient(id)));
    }

    @GetMapping
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
    @Operation(summary = "Delete Patient")
    public ResponseEntity<ApiResponse<Void>>
    delete(@PathVariable Long id) {

        patientService.deletePatient(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient deleted successfully"));
    }

    @GetMapping("/blood-groups")
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
}