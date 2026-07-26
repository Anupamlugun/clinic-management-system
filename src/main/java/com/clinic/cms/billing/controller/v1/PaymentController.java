package com.clinic.cms.billing.controller.v1;

import com.clinic.cms.billing.dto.v1.PaymentResponse;
import com.clinic.cms.billing.dto.v1.PaymentUpdateRequest;
import com.clinic.cms.billing.enums.PaymentMode;
import com.clinic.cms.billing.enums.PaymentStatus;
import com.clinic.cms.billing.service.PaymentService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
@Tag(
        name = "Payment",
        description = "Payment Management APIs")
public class PaymentController {

    private final PaymentService paymentService;


    @GetMapping("/{id}")
    @PreAuthorize("""
    hasAuthority('VIEW_PAYMENT') and
    hasAnyRole('SYSTEM_ADMIN','RECEPTIONIST','DOCTOR','PATIENT')
    """)
    @Operation(summary = "Get Payment By Id")
    public ResponseEntity<ApiResponse<PaymentResponse>>
    get(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment fetched successfully",
                        paymentService.getPayment(id)));
    }

    @GetMapping
    @PreAuthorize("""
    hasAuthority('VIEW_ALL_PAYMENTS') and
    hasAnyRole('SYSTEM_ADMIN','RECEPTIONIST')
    """)
    @Operation(summary = "Get All Payments")
    public ResponseEntity<ApiResponse<Page<PaymentResponse>>>
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
                        "Payments fetched successfully",
                        paymentService.getAllPayments(pageable)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("""
    hasAuthority('UPDATE_PAYMENT') and
    hasAnyRole('SYSTEM_ADMIN','RECEPTIONIST')
    """)
    @Operation(summary = "Update Payment")
    public ResponseEntity<ApiResponse<PaymentResponse>>
    update(
            @PathVariable Long id,
            @Valid
            @RequestBody
            PaymentUpdateRequest request) {

        PaymentResponse response = paymentService.updatePayment(id, request);
        return ResponseEntity.ok(
                ApiResponse.success(
                        response.paymentStatus().getDescription(), response));
    }

    @GetMapping("/modes")
    @PreAuthorize("""
    hasAuthority('VIEW_PAYMENT') and
    hasAnyRole('SYSTEM_ADMIN','RECEPTIONIST','DOCTOR','PATIENT')
    """)
    @Operation(summary = "Get Payment Modes")
    public ResponseEntity<ApiResponse<List<EnumResponse>>>
    getPaymentModes() {

        List<EnumResponse> modes =
                Arrays.stream(PaymentMode.values())
                        .map(mode ->
                                new EnumResponse(
                                        mode.name(),
                                        mode.getDisplayName()))
                        .toList();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment modes fetched successfully",
                        modes));
    }

    @GetMapping("/by-appointment/{appointmentId}")
    @PreAuthorize("""
    hasAuthority('VIEW_PAYMENT') and
    hasAnyRole('SYSTEM_ADMIN','RECEPTIONIST','DOCTOR','PATIENT')
    """)
    @Operation(summary = "Get Payment By Appointment Id")
    public ResponseEntity<ApiResponse<PaymentResponse>> getByAppointment(
            @PathVariable Long appointmentId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment fetched successfully",
                        paymentService.getPaymentByAppointmentId(appointmentId)));
    }

    @GetMapping("/completed")
    @PreAuthorize("""
    hasAuthority('VIEW_ALL_PAYMENTS') and
    hasAnyRole('SYSTEM_ADMIN','RECEPTIONIST')
    """)
    @Operation(summary = "Get Completed Payments")
    public ResponseEntity<ApiResponse<Page<PaymentResponse>>> getCompletedPayments(
            @ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "paidAt",
                    direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Completed payments fetched successfully",
                        paymentService.getPaymentsByStatus(
                                PaymentStatus.COMPLETED,
                                pageable)));
    }

    @GetMapping("/pending")
    @PreAuthorize("""
    hasAuthority('VIEW_ALL_PAYMENTS') and
    hasAnyRole('SYSTEM_ADMIN','RECEPTIONIST')
    """)
    @Operation(summary = "Get Pending Payments")
    public ResponseEntity<ApiResponse<Page<PaymentResponse>>> getPendingPayments(
            @ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Pending payments fetched successfully",
                        paymentService.getPaymentsByStatus(
                                PaymentStatus.PENDING,
                                pageable)));
    }

    @GetMapping("/failed")
    @PreAuthorize("""
    hasAuthority('VIEW_ALL_PAYMENTS') and
    hasAnyRole('SYSTEM_ADMIN','RECEPTIONIST')
    """)
    @Operation(summary = "Get Failed Payments")
    public ResponseEntity<ApiResponse<Page<PaymentResponse>>> getFailedPayments(
            @ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "updatedAt",
                    direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Failed payments fetched successfully",
                        paymentService.getPaymentsByStatus(
                                PaymentStatus.FAILED,
                                pageable)));
    }

    @GetMapping("/recent")
    @PreAuthorize("""
    hasAuthority('VIEW_ALL_PAYMENTS') and
    hasAnyRole('SYSTEM_ADMIN','RECEPTIONIST')
    """)
    @Operation(summary = "Get Recent Payments")
    public ResponseEntity<ApiResponse<Page<PaymentResponse>>> getRecentPayments(
            @ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Recent payments fetched successfully",
                        paymentService.getRecentPayments(pageable)));
    }
}