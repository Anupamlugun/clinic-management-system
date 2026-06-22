package com.clinic.cms.billing.workflow;

import com.clinic.cms.billing.enums.PaymentStatus;
import com.clinic.cms.common.workflow.WorkflowRules;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Component
public class PaymentWorkflowRules
        extends WorkflowRules<PaymentStatus> {

    private static final Map<PaymentStatus, Set<PaymentStatus>>
            ALLOWED_TRANSITIONS = Map.of(

            PaymentStatus.PENDING,
            Set.of(
                    PaymentStatus.COMPLETED,
                    PaymentStatus.FAILED
            ),

            PaymentStatus.COMPLETED,
            Set.of(
                    PaymentStatus.REFUNDED
            ),

            PaymentStatus.FAILED,
            Set.of(
                    PaymentStatus.PENDING
            ),

            PaymentStatus.REFUNDED,
            Collections.emptySet()
    );

    @Override
    protected Map<PaymentStatus, Set<PaymentStatus>>
    getAllowedTransitions() {
        return ALLOWED_TRANSITIONS;
    }
}