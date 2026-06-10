package com.clinic.cms.doctor.dto.v1;

import com.clinic.cms.doctor.enums.DoctorStatus;
import jakarta.validation.constraints.NotNull;

public record DoctorStatusUpdateRequest(
        @NotNull(message = "Status is required")
        DoctorStatus status
) {}
