package com.clinic.cms.billing.mapper.v1;

import com.clinic.cms.billing.dto.v1.PaymentResponse;
import com.clinic.cms.billing.dto.v1.PaymentUpdateRequest;
import com.clinic.cms.billing.entity.Payment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "appointmentId", source = "appointment.id")
    PaymentResponse toResponse(Payment payment);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "appointment", ignore = true)
    @Mapping(target = "paidAt", ignore = true)
    @Mapping(target = "receiptNumber", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    void updateEntityFromRequest(
            PaymentUpdateRequest request,
            @MappingTarget Payment payment
    );
}