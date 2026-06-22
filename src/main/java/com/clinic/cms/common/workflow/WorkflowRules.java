package com.clinic.cms.common.workflow;

import com.clinic.cms.exception.custom.InvalidWorkflowTransitionException;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public abstract class WorkflowRules<T extends Enum<T>> {

    protected abstract Map<T, Set<T>> getAllowedTransitions();

    public boolean isValidTransition(T currentStatus, T targetStatus) {
        return getAllowedTransitions()
                .getOrDefault(currentStatus, Collections.emptySet())
                .contains(targetStatus);
    }

    public void validateTransition(
            T currentStatus,
            T targetStatus) {

        if (!isValidTransition(currentStatus, targetStatus)) {

            throw new InvalidWorkflowTransitionException(
                    currentStatus,
                    targetStatus,
                    getAllowedTransitions(currentStatus)
            );
        }
    }

    public Set<T> getAllowedTransitions(T currentStatus) {
        return getAllowedTransitions()
                .getOrDefault(currentStatus, Collections.emptySet());
    }
}