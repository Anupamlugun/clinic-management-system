package com.clinic.cms.billing.service;

import com.clinic.cms.billing.dto.v1.PaymentResponse;
import com.clinic.cms.billing.dto.v1.PaymentUpdateRequest;
import com.clinic.cms.billing.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    Payment createPayment(
            Long requestId);

    PaymentResponse getPayment(
            Long id);

    Page<PaymentResponse> getAllPayments(
            Pageable pageable);

    PaymentResponse updatePayment(
            Long id,
            PaymentUpdateRequest request);
}