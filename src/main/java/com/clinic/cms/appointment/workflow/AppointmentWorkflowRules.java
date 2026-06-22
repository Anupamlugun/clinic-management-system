package com.clinic.cms.appointment.workflow;

import com.clinic.cms.appointment.enums.AppointmentStatus;
import com.clinic.cms.common.workflow.WorkflowRules;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Component
public class AppointmentWorkflowRules
        extends WorkflowRules<AppointmentStatus> {

    private static final Map<AppointmentStatus, Set<AppointmentStatus>>
            ALLOWED_TRANSITIONS = Map.of(

            AppointmentStatus.CONFIRMED,
            Set.of(
                    AppointmentStatus.CHECKED_IN,
                    AppointmentStatus.CANCELLED,
                    AppointmentStatus.NO_SHOW
            ),

            AppointmentStatus.CHECKED_IN,
            Set.of(AppointmentStatus.IN_CONSULTATION),

            AppointmentStatus.IN_CONSULTATION,
            Set.of(AppointmentStatus.COMPLETED),

            AppointmentStatus.COMPLETED,
            Set.of(AppointmentStatus.FOLLOW_UP_SCHEDULED),

            AppointmentStatus.FOLLOW_UP_SCHEDULED,
            Collections.emptySet(),

            AppointmentStatus.CANCELLED,
            Collections.emptySet(),

            AppointmentStatus.NO_SHOW,
            Collections.emptySet()
    );

    @Override
    protected Map<AppointmentStatus, Set<AppointmentStatus>>
    getAllowedTransitions() {
        return ALLOWED_TRANSITIONS;
    }
}