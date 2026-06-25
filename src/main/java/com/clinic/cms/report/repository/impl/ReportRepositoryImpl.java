package com.clinic.cms.report.repository.impl;

import com.clinic.cms.report.dto.v1.*;
import com.clinic.cms.report.repository.ReportRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DashboardResponse getDashboard() {

        // JPQL / Native SQL

        return null;
    }

    @Override
    public TodayReportResponse getTodayReport() {
        return null;
    }

    @Override
    public RevenueReportResponse getRevenue() {
        return null;
    }

    @Override
    public List<MonthlyRevenueResponse> getMonthlyRevenue() {
        return List.of();
    }

    @Override
    public List<YearlyRevenueResponse> getYearlyRevenue() {
        return List.of();
    }

    @Override
    public List<DoctorReportResponse> getDoctorWiseReport() {
        return List.of();
    }

    @Override
    public List<PatientReportResponse> getPatientWiseReport() {
        return List.of();
    }

    @Override
    public AppointmentSummaryResponse getAppointmentSummary() {
        return null;
    }

    @Override
    public PaymentSummaryResponse getPaymentSummary() {
        return null;
    }
}
