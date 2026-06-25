package com.clinic.cms.report.service;

import com.clinic.cms.report.dto.v1.AppointmentSummaryResponse;
import com.clinic.cms.report.dto.v1.DashboardResponse;
import com.clinic.cms.report.dto.v1.DoctorReportResponse;
import com.clinic.cms.report.dto.v1.MonthlyRevenueResponse;
import com.clinic.cms.report.dto.v1.PatientReportResponse;
import com.clinic.cms.report.dto.v1.PaymentSummaryResponse;
import com.clinic.cms.report.dto.v1.RevenueReportResponse;
import com.clinic.cms.report.dto.v1.TodayReportResponse;
import com.clinic.cms.report.dto.v1.YearlyRevenueResponse;

import java.util.List;

public interface ReportService {

    DashboardResponse getDashboard();

    TodayReportResponse getTodayReport();

    RevenueReportResponse getRevenue();

    List<MonthlyRevenueResponse> getMonthlyRevenue();

    List<YearlyRevenueResponse> getYearlyRevenue();

    List<DoctorReportResponse> getDoctorWiseReport();

    List<PatientReportResponse> getPatientWiseReport();

    AppointmentSummaryResponse getAppointmentSummary();

    PaymentSummaryResponse getPaymentSummary();
}