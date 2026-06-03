package com.clinic.cms.doctor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DoctorCreateRequest {

    @NotBlank
    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String phoneNumber;

    private String specialization;

    private Integer experienceYears;
}
