package com.clinic.cms.doctor.mapper;

import com.clinic.cms.doctor.dto.DoctorCreateRequest;
import com.clinic.cms.doctor.dto.DoctorResponse;
import com.clinic.cms.doctor.entity.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    Doctor toEntity(DoctorCreateRequest request);

    DoctorResponse toResponse(Doctor doctor);
}