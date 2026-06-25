package com.clinic.cms.report.controller.v1;

import com.clinic.cms.common.dto.v1.ApiResponse;
import com.clinic.cms.report.dto.v1.*;
import com.clinic.cms.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Reports Management APIs")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "Get dashboard report")
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardResponse>> getDashboard() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Dashboard report fetched successfully",
                        reportService.getDashboard()
                )
        );
    }

    @Operation(summary = "Get today's report")
    @GetMapping("/today")
    public ResponseEntity<ApiResponse<TodayReportResponse>> getTodayReport() {
        return ResponseEntity.ok(ApiResponse.success("Today's report fetched successfully", reportService.getTodayReport()));
    }

    @Operation(summary = "Get revenue report")
    @GetMapping("/revenue")
    public ResponseEntity<ApiResponse<RevenueReportResponse>> getRevenue() {
        return ResponseEntity.ok(ApiResponse.success("Revenue report fetched successfully", reportService.getRevenue()));
    }

    @Operation(summary = "Get monthly revenue report")
    @GetMapping("/revenue/monthly")
    public ResponseEntity<ApiResponse<List<MonthlyRevenueResponse>>> getMonthlyRevenue() {
        return ResponseEntity.ok(ApiResponse.success("Monthly revenue report fetched successfully", reportService.getMonthlyRevenue()));
    }

    @Operation(summary = "Get yearly revenue report")
    @GetMapping("/revenue/yearly")
    public ResponseEntity<ApiResponse<List<YearlyRevenueResponse>>> getYearlyRevenue() {
        return ResponseEntity.ok(ApiResponse.success("Yearly revenue report fetched successfully", reportService.getYearlyRevenue()));
    }

    @Operation(summary = "Get doctor-wise report")
    @GetMapping("/doctor-wise")
    public ResponseEntity<ApiResponse<List<DoctorReportResponse>>> getDoctorWiseReport() {
        return ResponseEntity.ok(ApiResponse.success("Doctor-wise report fetched successfully", reportService.getDoctorWiseReport()));
    }

    @Operation(summary = "Get patient-wise report")
    @GetMapping("/patient-wise")
    public ResponseEntity<ApiResponse<List<PatientReportResponse>>> getPatientWiseReport() {
        return ResponseEntity.ok(ApiResponse.success("Patient-wise report fetched successfully", reportService.getPatientWiseReport()));
    }

    @Operation(summary = "Get appointment summary")
    @GetMapping("/appointment-summary")
    public ResponseEntity<ApiResponse<AppointmentSummaryResponse>> getAppointmentSummary() {
        return ResponseEntity.ok(ApiResponse.success("Appointment summary fetched successfully", reportService.getAppointmentSummary()));
    }

    @Operation(summary = "Get payment summary")
    @GetMapping("/payment-summary")
    public ResponseEntity<ApiResponse<PaymentSummaryResponse>> getPaymentSummary() {
        return ResponseEntity.ok(ApiResponse.success("Payment summary fetched successfully", reportService.getPaymentSummary()));
    }

}
