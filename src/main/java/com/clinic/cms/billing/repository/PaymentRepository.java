package com.clinic.cms.billing.repository;

import com.clinic.cms.billing.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository
        extends JpaRepository<Payment, Long> {
}