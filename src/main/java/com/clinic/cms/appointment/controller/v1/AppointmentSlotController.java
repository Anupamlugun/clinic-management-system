package com.clinic.cms.appointment.controller.v1;

import com.clinic.cms.appointment.dto.v1.AppointmentSlotResponse;
import com.clinic.cms.appointment.service.AppointmentSlotService;
import com.clinic.cms.common.dto.v1.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/appointment-slots")
@RequiredArgsConstructor
@Tag(
        name = "Appointment Slot",
        description = "Appointment Slot Management APIs")
public class AppointmentSlotController {

    private final AppointmentSlotService service;

    @GetMapping("/{id}")
    @Operation(summary = "Get Appointment Slot By Id")
    public ResponseEntity<ApiResponse<AppointmentSlotResponse>>
    get(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment slot fetched successfully",
                        service.getSlot(id)));
    }

    @GetMapping
    @Operation(summary = "Get All Appointment Slots")
    public ResponseEntity<ApiResponse<Page<AppointmentSlotResponse>>>
    getAll(
            @ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC)
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment slots fetched successfully",
                        service.getAllSlots(pageable)));
    }

    @GetMapping("/search")
    @Operation(summary = "Get Slots By Doctor And Date")
    public ResponseEntity<ApiResponse<Page<AppointmentSlotResponse>>>
    getByDoctorAndDate(
            @RequestParam Long doctorId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate slotDate,
            @ParameterObject
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment slots fetched successfully",
                        service.getSlotsByDoctorAndDate(
                                doctorId,
                                slotDate,
                                pageable)));
    }

    @GetMapping("/available")
    @Operation(summary = "Get Available Appointment Slots")
    public ResponseEntity<ApiResponse<Page<AppointmentSlotResponse>>> getAvailableSlots(
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Available appointment slots fetched successfully",
                        service.getAvailableSlots(pageable)));
    }

    @GetMapping("/booked")
    @Operation(summary = "Get Booked Appointment Slots")
    public ResponseEntity<ApiResponse<Page<AppointmentSlotResponse>>> getBookedSlots(
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Booked appointment slots fetched successfully",
                        service.getBookedSlots(pageable)));
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate Appointment Slot")
    public ResponseEntity<ApiResponse<AppointmentSlotResponse>> activateSlot(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment slot activated successfully",
                        service.activateSlot(id)));
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate Appointment Slot")
    public ResponseEntity<ApiResponse<AppointmentSlotResponse>> deactivateSlot(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment slot deactivated successfully",
                        service.deactivateSlot(id)));
    }
}