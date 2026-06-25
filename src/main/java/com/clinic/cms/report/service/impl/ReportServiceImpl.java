package com.clinic.cms.report.service.impl;

import com.clinic.cms.report.dto.v1.AppointmentSummaryResponse;
import com.clinic.cms.report.dto.v1.DashboardResponse;
import com.clinic.cms.report.dto.v1.DoctorReportResponse;
import com.clinic.cms.report.dto.v1.MonthlyRevenueResponse;
import com.clinic.cms.report.dto.v1.PatientReportResponse;
import com.clinic.cms.report.dto.v1.PaymentSummaryResponse;
import com.clinic.cms.report.dto.v1.RevenueReportResponse;
import com.clinic.cms.report.dto.v1.TodayReportResponse;
import com.clinic.cms.report.dto.v1.YearlyRevenueResponse;
import com.clinic.cms.report.repository.ReportRepository;
import com.clinic.cms.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Override
    public DashboardResponse getDashboard() {
        return reportRepository.getDashboard();
    }

    @Override
    public TodayReportResponse getTodayReport() {
        return reportRepository.getTodayReport();
    }

    @Override
    public RevenueReportResponse getRevenue() {
        return reportRepository.getRevenue();
    }

    @Override
    public List<MonthlyRevenueResponse> getMonthlyRevenue() {
        return reportRepository.getMonthlyRevenue();
    }

    @Override
    public List<YearlyRevenueResponse> getYearlyRevenue() {
        return reportRepository.getYearlyRevenue();
    }

    @Override
    public List<DoctorReportResponse> getDoctorWiseReport() {
        return reportRepository.getDoctorWiseReport();
    }

    @Override
    public List<PatientReportResponse> getPatientWiseReport() {
        return reportRepository.getPatientWiseReport();
    }

    @Override
    public AppointmentSummaryResponse getAppointmentSummary() {
        return reportRepository.getAppointmentSummary();
    }

    @Override
    public PaymentSummaryResponse getPaymentSummary() {
        return reportRepository.getPaymentSummary();
    }
}