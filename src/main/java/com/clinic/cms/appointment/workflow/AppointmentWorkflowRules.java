package com.clinic.cms.appointment.workflow;

import com.clinic.cms.appointment.enums.AppointmentStatus;
import com.clinic.cms.exception.custom.InvalidAppointmentStatusTransitionException;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public final class AppointmentWorkflowRules {

    private AppointmentWorkflowRules() {
        throw new UnsupportedOperationException(
                "Utility class cannot be instantiated");
    }

    private static final Map<AppointmentStatus, Set<AppointmentStatus>> ALLOWED_TRANSITIONS =
            Map.of(

                    AppointmentStatus.PENDING,
                    Set.of(
                            AppointmentStatus.CONFIRMED,
                            AppointmentStatus.CANCELLED
                    ),

                    AppointmentStatus.CONFIRMED,
                    Set.of(
                            AppointmentStatus.PAYMENT_PENDING,
                            AppointmentStatus.CANCELLED,
                            AppointmentStatus.NO_SHOW
                    ),

                    AppointmentStatus.PAYMENT_PENDING,
                    Set.of(
                            AppointmentStatus.PAYMENT_COMPLETED,
                            AppointmentStatus.CANCELLED
                    ),

                    AppointmentStatus.PAYMENT_COMPLETED,
                    Set.of(
                            AppointmentStatus.CHECKED_IN,
                            AppointmentStatus.CANCELLED
                    ),

                    AppointmentStatus.CHECKED_IN,
                    Set.of(
                            AppointmentStatus.IN_CONSULTATION
                    ),

                    AppointmentStatus.IN_CONSULTATION,
                    Set.of(
                            AppointmentStatus.COMPLETED
                    ),

                    AppointmentStatus.COMPLETED,
                    Set.of(
                            AppointmentStatus.FOLLOW_UP_SCHEDULED
                    ),

                    AppointmentStatus.FOLLOW_UP_SCHEDULED,
                    Collections.emptySet(),

                    AppointmentStatus.CANCELLED,
                    Collections.emptySet(),

                    AppointmentStatus.NO_SHOW,
                    Collections.emptySet()
            );

    /**
     * Checks whether transition is allowed.
     */
    public static boolean isValidTransition(
            AppointmentStatus currentStatus,
            AppointmentStatus targetStatus) {

        return ALLOWED_TRANSITIONS
                .getOrDefault(currentStatus, Collections.emptySet())
                .contains(targetStatus);
    }

    /**
     * Validates status transition.
     * Throws exception if transition is invalid.
     */
    public static void validateTransition(
            AppointmentStatus currentStatus,
            AppointmentStatus targetStatus) {

        if (!isValidTransition(currentStatus, targetStatus)) {

            throw new InvalidAppointmentStatusTransitionException(
                    currentStatus,
                    targetStatus
            );
        }
    }

    /**
     * Returns all allowed next statuses.
     */
    public static Set<AppointmentStatus> getAllowedTransitions(
            AppointmentStatus currentStatus) {

        return ALLOWED_TRANSITIONS.getOrDefault(
                currentStatus,
                Collections.emptySet()
        );
    }
}