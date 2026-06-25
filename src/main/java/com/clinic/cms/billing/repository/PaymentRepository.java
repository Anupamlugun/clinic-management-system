package com.clinic.cms.billing.repository;

import com.clinic.cms.billing.entity.Payment;
import com.clinic.cms.billing.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    Optional<Payment> findByAppointmentId(Long appointmentId);

    Page<Payment> findByAppointmentPatientId(
            Long patientId,
            Pageable pageable);

    Page<Payment> findByPaymentStatus(
            PaymentStatus paymentStatus,
            Pageable pageable);

    Page<Payment> findAllByOrderByCreatedAtDesc(
            Pageable pageable);
}