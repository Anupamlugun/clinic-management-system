package com.clinic.cms.patient.dto.v1;

import com.clinic.cms.common.enums.Gender;
import com.clinic.cms.patient.enums.BloodGroup;

import java.time.LocalDate;

public record PatientUpdateRequest(

        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate dateOfBirth,
        Gender gender,
        BloodGroup bloodGroup,
        String emergencyContactName,
        String emergencyContactNumber,
        String address
) {
}