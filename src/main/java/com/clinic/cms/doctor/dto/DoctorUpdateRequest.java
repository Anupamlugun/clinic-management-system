package com.clinic.cms.doctor.dto;

import lombok.Data;

@Data
public class DoctorUpdateRequest {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String specialization;

    private Integer experienceYears;

    private Boolean active;
}
