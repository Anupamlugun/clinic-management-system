package com.clinic.cms.doctor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String specialization;

    private Integer experienceYears;

    private Boolean active;
}
