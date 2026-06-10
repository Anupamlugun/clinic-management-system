package com.clinic.cms.doctor.controller.v1;

import com.clinic.cms.common.dto.v1.ApiResponse;
import com.clinic.cms.doctor.dto.v1.DoctorCreateRequest;
import com.clinic.cms.doctor.dto.v1.DoctorResponse;
import com.clinic.cms.doctor.dto.v1.DoctorStatusUpdateRequest;
import com.clinic.cms.doctor.dto.v1.DoctorUpdateRequest;
import com.clinic.cms.doctor.service.DoctorService;
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

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctor", description = "Doctor Management APIs")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    @Operation(summary = "Create Doctor")
    public ResponseEntity<ApiResponse<DoctorResponse>> create(
            @Valid @RequestBody DoctorCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Doctor created successfully",
                        doctorService.createDoctor(request)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Doctor By Id")
    public ResponseEntity<ApiResponse<DoctorResponse>> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor fetch successfully",
                        doctorService.getDoctor(id)));
    }

    @GetMapping
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
    @Operation(summary = "Delete Doctor")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        doctorService.deleteDoctor(id);

        return ResponseEntity.ok(
                ApiResponse.success("Doctor deleted successfully"));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "update doctor status")
    public ResponseEntity<ApiResponse<DoctorResponse>> updateStatus(@PathVariable Long id, DoctorStatusUpdateRequest request) {

        return ResponseEntity.ok(ApiResponse.success("Doctor status updated successfully", doctorService.updateDoctorStatus(id, request)));

    }
}
