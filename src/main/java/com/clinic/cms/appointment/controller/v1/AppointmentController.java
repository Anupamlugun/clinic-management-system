package com.clinic.cms.appointment.controller.v1;

import com.clinic.cms.appointment.dto.v1.*;
import com.clinic.cms.appointment.enums.AppointmentStatus;
import com.clinic.cms.appointment.service.AppointmentService;
import com.clinic.cms.common.dto.v1.ApiResponse;
import com.clinic.cms.common.dto.v1.EnumResponse;
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
@RequestMapping("/v1/appointments")
@RequiredArgsConstructor
@Tag(
        name = "Appointment",
        description = "Appointment Management APIs"
)
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @Operation(summary = "Create Appointment")
    public ResponseEntity<ApiResponse<AppointmentResponse>>
    create(
            @Valid
            @RequestBody
            AppointmentCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Appointment has been confirmed and scheduled",
                        appointmentService
                                .createAppointment(request)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Appointment By Id")
    public ResponseEntity<ApiResponse<AppointmentResponse>>
    get(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment fetched successfully",
                        appointmentService
                                .getAppointment(id)));
    }

    @GetMapping
    @Operation(summary = "Get All Appointments")
    public ResponseEntity<ApiResponse<Page<AppointmentResponse>>>
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
                        "Appointments fetched successfully",
                        appointmentService
                                .getAllAppointments(pageable)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Appointment")
    public ResponseEntity<ApiResponse<AppointmentResponse>>
    update(
            @PathVariable Long id,
            @Valid
            @RequestBody
            AppointmentUpdateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment updated successfully",
                        appointmentService
                                .updateAppointment(id, request)));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update Appointment Status")
    public ResponseEntity<ApiResponse<AppointmentResponse>>
    updateStatus(
            @PathVariable Long id,
            @Valid
            @RequestBody
            AppointmentStatusUpdateRequest request) {

        AppointmentResponse response = appointmentService.updateStatus(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        response.status().getDescription(), response));
    }


    @GetMapping("/status")
    @Operation(summary = "Get Appointment Status")
    public ResponseEntity<ApiResponse<List<EnumResponse>>>
    getStatuses() {

        List<EnumResponse> statuses =
                Arrays.stream(
                                AppointmentStatus.values())
                        .map(status ->
                                new EnumResponse(
                                        status.name(),
                                        status.getDisplayName()))
                        .toList();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment statuses fetched successfully",
                        statuses));
    }

    @GetMapping("/by-patient/{patientId}")
    @Operation(summary = "Get Appointments By Patient")
    public ResponseEntity<ApiResponse<Page<AppointmentResponse>>> getByPatient(
            @PathVariable Long patientId,
            @ParameterObject
            @PageableDefault(size = 10, sort = "appointmentDate")
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointments fetched successfully",
                        appointmentService.getAppointmentsByPatient(patientId, pageable)));
    }

    @GetMapping("/by-doctor/{doctorId}")
    @Operation(summary = "Get Appointments By Doctor")
    public ResponseEntity<ApiResponse<Page<AppointmentResponse>>> getByDoctor(
            @PathVariable Long doctorId,
            @ParameterObject
            @PageableDefault(size = 10, sort = "appointmentDate")
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointments fetched successfully",
                        appointmentService.getAppointmentsByDoctor(doctorId, pageable)));
    }

    @GetMapping("/by-status/{status}")
    @Operation(summary = "Get Appointments By Status")
    public ResponseEntity<ApiResponse<Page<AppointmentResponse>>> getByStatus(
            @PathVariable AppointmentStatus status,
            @ParameterObject
            @PageableDefault(size = 10, sort = "appointmentDate")
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointments fetched successfully",
                        appointmentService.getAppointmentsByStatus(status, pageable)));
    }

    @GetMapping("/by-date/{date}")
    @Operation(summary = "Get Appointments By Date")
    public ResponseEntity<ApiResponse<Page<AppointmentResponse>>> getByDate(
            @PathVariable java.time.LocalDate date,
            @ParameterObject
            @PageableDefault(size = 10, sort = "slot.startTime")
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointments fetched successfully",
                        appointmentService.getAppointmentsByDate(date, pageable)));
    }

    @GetMapping("/today")
    @Operation(summary = "Get Today's Appointments")
    public ResponseEntity<ApiResponse<Page<AppointmentResponse>>> getToday(
            @ParameterObject
            @PageableDefault(size = 10, sort = "slot.startTime")
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Today's appointments fetched successfully",
                        appointmentService.getTodayAppointments(pageable)));
    }

    @GetMapping("/upcoming")
    @Operation(summary = "Get Upcoming Appointments")
    public ResponseEntity<ApiResponse<Page<AppointmentResponse>>> getUpcoming(
            @ParameterObject
            @PageableDefault(size = 10, sort = "appointmentDate")
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Upcoming appointments fetched successfully",
                        appointmentService.getUpcomingAppointments(pageable)));
    }

    @GetMapping("/history/{patientId}")
    @Operation(summary = "Get Patient Appointment History")
    public ResponseEntity<ApiResponse<Page<AppointmentResponse>>> getHistory(
            @PathVariable Long patientId,
            @ParameterObject
            @PageableDefault(size = 10, sort = "appointmentDate", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment history fetched successfully",
                        appointmentService.getAppointmentHistory(patientId, pageable)));
    }
}