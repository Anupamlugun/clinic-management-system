package com.clinic.cms.report.repository.impl;

import com.clinic.cms.report.dto.v1.*;
import com.clinic.cms.report.repository.ReportRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepository {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public DashboardResponse getDashboard() {

        String sql = """
                SELECT
                    (SELECT COUNT(*) FROM patients),
                    (SELECT COUNT(*) FROM doctors WHERE active = true),
                    (SELECT COUNT(*) FROM appointments),
                    (SELECT COUNT(*) FROM appointments
                        WHERE appointment_date = CURRENT_DATE),
                    (SELECT COUNT(*) FROM appointments
                        WHERE status='COMPLETED'),
                    (SELECT COUNT(*) FROM appointments
                        WHERE status='CANCELLED'),
                    (
                        SELECT COALESCE(SUM(amount),0)
                        FROM payments
                        WHERE payment_status='COMPLETED'
                    ),
                    (
                        SELECT COALESCE(SUM(amount),0)
                        FROM payments
                        WHERE payment_status='COMPLETED'
                        AND DATE(paid_at)=CURRENT_DATE
                    )
                """;

        Object[] row = (Object[]) entityManager
                .createNativeQuery(sql)
                .getSingleResult();

        return new DashboardResponse(
                ((Number) row[0]).longValue(),
                ((Number) row[1]).longValue(),
                ((Number) row[2]).longValue(),
                ((Number) row[3]).longValue(),
                ((Number) row[4]).longValue(),
                ((Number) row[5]).longValue(),
                (BigDecimal) row[6],
                (BigDecimal) row[7]
        );
    }

    @Override
    public TodayReportResponse getTodayReport() {

        String sql = """
            SELECT
                COUNT(*) AS total_appointments,

                COUNT(*) FILTER (
                    WHERE status = 'COMPLETED'
                ) AS completed_appointments,

                COUNT(*) FILTER (
                    WHERE status = 'CANCELLED'
                ) AS cancelled_appointments,

                COUNT(*) FILTER (
                    WHERE status IN (
                        'CONFIRMED',
                        'CHECKED_IN',
                        'IN_CONSULTATION',
                        'FOLLOW_UP_SCHEDULED'
                    )
                ) AS pending_appointments,

                COALESCE(
                    (
                        SELECT SUM(amount)
                        FROM payments
                        WHERE payment_status = 'COMPLETED'
                          AND DATE(paid_at) = CURRENT_DATE
                    ),
                    0
                ) AS today_revenue

            FROM appointments
            WHERE appointment_date = CURRENT_DATE
            """;

        Object[] row = (Object[]) entityManager
                .createNativeQuery(sql)
                .getSingleResult();

        return new TodayReportResponse(
                toLong(row[0]),
                toLong(row[1]),
                toLong(row[2]),
                toLong(row[3]),
                toBigDecimal(row[4])
        );
    }

    private long toLong(Object value) {
        return value == null ? 0L : ((Number) value).longValue();
    }

    private BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }

        if (value instanceof BigDecimal bd) {
            return bd;
        }

        if (value instanceof Number number) {
            return BigDecimal.valueOf(number.doubleValue());
        }

        return new BigDecimal(value.toString());
    }

    @Override
    public RevenueReportResponse getRevenue() {

        String sql = """
                SELECT

                    COALESCE(SUM(
                        CASE
                        WHEN DATE(paid_at)=CURRENT_DATE
                        THEN amount
                        ELSE 0
                        END
                    ),0),

                    COALESCE(SUM(
                        CASE
                        WHEN DATE_TRUNC('week',paid_at)
                            =DATE_TRUNC('week',CURRENT_DATE)
                        THEN amount
                        ELSE 0
                        END
                    ),0),

                    COALESCE(SUM(
                        CASE
                        WHEN DATE_TRUNC('month',paid_at)
                            =DATE_TRUNC('month',CURRENT_DATE)
                        THEN amount
                        ELSE 0
                        END
                    ),0),

                    COALESCE(SUM(
                        CASE
                        WHEN DATE_TRUNC('year',paid_at)
                            =DATE_TRUNC('year',CURRENT_DATE)
                        THEN amount
                        ELSE 0
                        END
                    ),0),

                    COALESCE(SUM(amount),0)

                FROM payments
                WHERE payment_status='COMPLETED'
                """;

        Object[] row = (Object[]) entityManager
                .createNativeQuery(sql)
                .getSingleResult();

        return new RevenueReportResponse(
                (BigDecimal) row[0],
                (BigDecimal) row[1],
                (BigDecimal) row[2],
                (BigDecimal) row[3],
                (BigDecimal) row[4]
        );
    }

    @Override
    public List<MonthlyRevenueResponse> getMonthlyRevenue() {

        String sql = """
            SELECT
                EXTRACT(YEAR FROM paid_at) AS year,
                EXTRACT(MONTH FROM paid_at) AS month,
                COALESCE(SUM(amount),0) AS revenue
            FROM payments
            WHERE payment_status = 'COMPLETED'
            GROUP BY
                EXTRACT(YEAR FROM paid_at),
                EXTRACT(MONTH FROM paid_at)
            ORDER BY
                EXTRACT(YEAR FROM paid_at),
                EXTRACT(MONTH FROM paid_at)
            """;

        List<Object[]> rows = entityManager
                .createNativeQuery(sql)
                .getResultList();

        return rows.stream()
                .map(row -> new MonthlyRevenueResponse(
                        ((Number) row[0]).intValue(),
                        ((Number) row[1]).intValue(),
                        (BigDecimal) row[2]
                ))
                .toList();
    }

    @Override
    public List<YearlyRevenueResponse> getYearlyRevenue() {

        String sql = """
            SELECT
                EXTRACT(YEAR FROM paid_at) AS year,
                COALESCE(SUM(amount),0) AS revenue
            FROM payments
            WHERE payment_status='COMPLETED'
            GROUP BY EXTRACT(YEAR FROM paid_at)
            ORDER BY EXTRACT(YEAR FROM paid_at)
            """;

        List<Object[]> rows = entityManager
                .createNativeQuery(sql)
                .getResultList();

        return rows.stream()
                .map(row -> new YearlyRevenueResponse(
                        ((Number) row[0]).intValue(),
                        (BigDecimal) row[1]
                ))
                .toList();
    }

    @Override
    public List<DoctorReportResponse> getDoctorWiseReport() {

        String sql = """
            SELECT
                d.id,
                CONCAT(d.first_name,' ',COALESCE(d.last_name,'')) AS doctor_name,

                COUNT(a.id) AS total_appointments,

                SUM(
                    CASE
                        WHEN a.status='COMPLETED'
                        THEN 1
                        ELSE 0
                    END
                ) AS completed,

                SUM(
                    CASE
                        WHEN a.status='CANCELLED'
                        THEN 1
                        ELSE 0
                    END
                ) AS cancelled,

                COALESCE(SUM(
                    CASE
                        WHEN p.payment_status='COMPLETED'
                        THEN p.amount
                        ELSE 0
                    END
                ),0) AS revenue

            FROM doctors d

            LEFT JOIN appointments a
                ON a.doctor_id=d.id

            LEFT JOIN payments p
                ON p.appointment_id=a.id

            GROUP BY
                d.id,
                d.first_name,
                d.last_name

            ORDER BY doctor_name
            """;

        List<Object[]> rows = entityManager
                .createNativeQuery(sql)
                .getResultList();

        return rows.stream()
                .map(row -> new DoctorReportResponse(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        ((Number) row[2]).longValue(),
                        ((Number) row[3]).longValue(),
                        ((Number) row[4]).longValue(),
                        (BigDecimal) row[5]
                ))
                .toList();
    }

    @Override
    public List<PatientReportResponse> getPatientWiseReport() {

        String sql = """
            SELECT
                pt.id,

                CONCAT(pt.first_name,' ',COALESCE(pt.last_name,'')) AS patient_name,

                COUNT(a.id) AS total_visits,

                MAX(a.appointment_date) AS last_visit,

                COALESCE(SUM(
                    CASE
                        WHEN p.payment_status='COMPLETED'
                        THEN p.amount
                        ELSE 0
                    END
                ),0) AS total_paid

            FROM patients pt

            LEFT JOIN appointments a
                ON a.patient_id=pt.id

            LEFT JOIN payments p
                ON p.appointment_id=a.id

            GROUP BY
                pt.id,
                pt.first_name,
                pt.last_name

            ORDER BY patient_name
            """;

        List<Object[]> rows = entityManager
                .createNativeQuery(sql)
                .getResultList();

        return rows.stream()
                .map(row -> new PatientReportResponse(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        ((Number) row[2]).longValue(),
                        row[3] == null
                                ? null
                                : ((java.sql.Date) row[3]).toLocalDate(),
                        (BigDecimal) row[4]
                ))
                .toList();
    }

    @Override
    public AppointmentSummaryResponse getAppointmentSummary() {

        String sql = """
            SELECT

                COUNT(*) AS total,

                SUM(
                    CASE
                        WHEN status='SCHEDULED'
                        THEN 1
                        ELSE 0
                    END
                ) AS scheduled,

                SUM(
                    CASE
                        WHEN status='CHECKED_IN'
                        THEN 1
                        ELSE 0
                    END
                ) AS checked_in,

                SUM(
                    CASE
                        WHEN status='IN_CONSULTATION'
                        THEN 1
                        ELSE 0
                    END
                ) AS in_consultation,

                SUM(
                    CASE
                        WHEN status='COMPLETED'
                        THEN 1
                        ELSE 0
                    END
                ) AS completed,

                SUM(
                    CASE
                        WHEN status='CANCELLED'
                        THEN 1
                        ELSE 0
                    END
                ) AS cancelled,

                SUM(
                    CASE
                        WHEN status='NO_SHOW'
                        THEN 1
                        ELSE 0
                    END
                ) AS no_show

            FROM appointments
            """;

        Object[] row = (Object[]) entityManager
                .createNativeQuery(sql)
                .getSingleResult();

        return new AppointmentSummaryResponse(
                ((Number) row[0]).longValue(),
                ((Number) row[1]).longValue(),
                ((Number) row[2]).longValue(),
                ((Number) row[3]).longValue(),
                ((Number) row[4]).longValue(),
                ((Number) row[5]).longValue(),
                ((Number) row[6]).longValue()
        );
    }

    @Override
    public PaymentSummaryResponse getPaymentSummary() {

        String sql = """
            SELECT

                COUNT(*) AS total_payments,

                SUM(
                    CASE
                        WHEN payment_status='COMPLETED'
                        THEN 1
                        ELSE 0
                    END
                ) AS completed,

                SUM(
                    CASE
                        WHEN payment_status='PENDING'
                        THEN 1
                        ELSE 0
                    END
                ) AS pending,

                SUM(
                    CASE
                        WHEN payment_status='FAILED'
                        THEN 1
                        ELSE 0
                    END
                ) AS failed,

                SUM(
                    CASE
                        WHEN payment_status='REFUNDED'
                        THEN 1
                        ELSE 0
                    END
                ) AS refunded,

                COALESCE(SUM(
                    CASE
                        WHEN payment_status='COMPLETED'
                        THEN amount
                        ELSE 0
                    END
                ),0) AS collected_amount,

                COALESCE(SUM(
                    CASE
                        WHEN payment_status='PENDING'
                        THEN amount
                        ELSE 0
                    END
                ),0) AS pending_amount

            FROM payments
            """;

        Object[] row = (Object[]) entityManager
                .createNativeQuery(sql)
                .getSingleResult();

        return new PaymentSummaryResponse(
                ((Number) row[0]).longValue(),
                ((Number) row[1]).longValue(),
                ((Number) row[2]).longValue(),
                ((Number) row[3]).longValue(),
                ((Number) row[4]).longValue(),
                (BigDecimal) row[5],
                (BigDecimal) row[6]
        );
    }
}
