package com.clinic.cms.patient.dto.v1;

import com.clinic.cms.common.enums.Gender;
import com.clinic.cms.patient.enums.BloodGroup;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PatientCreateRequest(

        @NotBlank
        @Size(max = 100)
        String firstName,

        @Size(max = 100)
        String lastName,

        @Email
        @NotBlank
        String email,

        @Size(max = 20)
        String phoneNumber,

        @NotNull
        LocalDate dateOfBirth,

        @NotNull
        Gender gender,

        @NotNull
        BloodGroup bloodGroup,

        String emergencyContactName,

        String emergencyContactNumber,

        String address
) {
}