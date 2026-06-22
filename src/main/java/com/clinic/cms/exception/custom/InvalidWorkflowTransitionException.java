package com.clinic.cms.exception.custom;

import java.util.Set;
import java.util.stream.Collectors;

public class InvalidWorkflowTransitionException
        extends RuntimeException {

    public InvalidWorkflowTransitionException(
            Enum<?> currentStatus,
            Enum<?> targetStatus,
            Set<? extends Enum<?>> allowedStatuses) {

        super(String.format(
                "Status cannot be moved from '%s' to '%s'. Allowed next status(es): %s.",
                currentStatus.name(),
                targetStatus.name(),
                allowedStatuses.stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(", "))
        ));
    }
}