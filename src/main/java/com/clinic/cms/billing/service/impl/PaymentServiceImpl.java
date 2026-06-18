package com.clinic.cms.billing.service.impl;

import com.clinic.cms.appointment.entity.Appointment;
import com.clinic.cms.appointment.repository.AppointmentRepository;
import com.clinic.cms.billing.dto.v1.PaymentResponse;
import com.clinic.cms.billing.dto.v1.PaymentUpdateRequest;
import com.clinic.cms.billing.entity.Payment;
import com.clinic.cms.billing.enums.PaymentStatus;
import com.clinic.cms.billing.mapper.v1.PaymentMapper;
import com.clinic.cms.billing.repository.PaymentRepository;
import com.clinic.cms.billing.service.PaymentService;
import com.clinic.cms.config.properties.BillingProperties;
import com.clinic.cms.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl
        implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;
    private final PaymentMapper paymentMapper;
    private final BillingProperties billingProperties;

    @Override
    public PaymentResponse createPayment(Long appointmentId) {

        Appointment appointment =
                appointmentRepository.findById(appointmentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Appointment not found"));

        Payment payment = new Payment();

        payment.setAppointment(appointment);
        payment.setAmount(BigDecimal.valueOf(500));
        payment.setPaymentStatus(PaymentStatus.PENDING);

        Payment saved = paymentRepository.save(payment);

        return paymentMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPayment(
            Long id) {

        Payment payment =
                paymentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payment not found"));

        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponse> getAllPayments(
            Pageable pageable) {

        return paymentRepository
                .findAll(pageable)
                .map(paymentMapper::toResponse);
    }

    @Override
    public PaymentResponse updatePayment(
            Long id,
            PaymentUpdateRequest request) {

        Payment payment =
                paymentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payment not found"));

        paymentMapper.updateEntityFromRequest(
                request,
                payment);

        payment.setPaidAt(LocalDateTime.now());

        Payment updated =
                paymentRepository.save(payment);

        return paymentMapper.toResponse(updated);
    }

    private String generateReceiptNumber() {
        return billingProperties.getReceiptPrefix()
                + "-"
                + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }
}