package com.clinic.cms.doctor.controller.v1;

import com.clinic.cms.common.dto.v1.ApiResponse;
import com.clinic.cms.doctor.dto.v1.DoctorScheduleCreateRequest;
import com.clinic.cms.doctor.dto.v1.DoctorScheduleResponse;
import com.clinic.cms.doctor.dto.v1.DoctorScheduleUpdateRequest;
import com.clinic.cms.doctor.service.DoctorScheduleService;
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
@RequestMapping("/v1/doctor-schedules")
@RequiredArgsConstructor
@Tag(
        name = "Doctor Schedule",
        description = "Doctor Schedule Management APIs")
public class DoctorScheduleController {

    private final DoctorScheduleService scheduleService;

    @PostMapping
    @PreAuthorize("""
        hasAuthority('CREATE_DOCTOR_SCHEDULE') &&
        (hasRole('SYSTEM_ADMIN') || hasRole('RECEPTIONIST'))
    """)
    @Operation(summary = "Create Doctor Schedule")
    public ResponseEntity<ApiResponse<DoctorScheduleResponse>> create(
            @Valid @RequestBody DoctorScheduleCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Doctor schedule created successfully",
                        scheduleService.createSchedule(request)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("""
        hasAuthority('VIEW_DOCTOR_SCHEDULE') &&
        (hasRole('SYSTEM_ADMIN')
            || hasRole('RECEPTIONIST')
            || hasRole('DOCTOR'))
    """)
    @Operation(summary = "Get Doctor Schedule By Id")
    public ResponseEntity<ApiResponse<DoctorScheduleResponse>> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor schedule fetched successfully",
                        scheduleService.getSchedule(id)));
    }

    @GetMapping
    @PreAuthorize("""
        hasAuthority('VIEW_ALL_DOCTOR_SCHEDULES') &&
        (hasRole('SYSTEM_ADMIN')
            || hasRole('RECEPTIONIST'))
    """)
    @Operation(summary = "Get All Doctor Schedules")
    public ResponseEntity<ApiResponse<Page<DoctorScheduleResponse>>> getAll(
            @ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC)
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor schedules fetched successfully",
                        scheduleService.getAllSchedules(pageable)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("""
        hasAuthority('UPDATE_DOCTOR_SCHEDULE') &&
        (hasRole('SYSTEM_ADMIN')
            || hasRole('RECEPTIONIST'))
    """)
    @Operation(summary = "Update Doctor Schedule")
    public ResponseEntity<ApiResponse<DoctorScheduleResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody DoctorScheduleUpdateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor schedule updated successfully",
                        scheduleService.updateSchedule(
                                id,
                                request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("""
        hasAuthority('DELETE_DOCTOR_SCHEDULE') &&
        hasRole('SYSTEM_ADMIN')
    """)
    @Operation(summary = "Delete Doctor Schedule")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        scheduleService.deleteSchedule(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor schedule deleted successfully"));
    }
}