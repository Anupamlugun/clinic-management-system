package com.clinic.cms.doctor.mapper.v1;

import com.clinic.cms.doctor.dto.v1.DoctorSpecializationCreateRequest;
import com.clinic.cms.doctor.dto.v1.DoctorSpecializationResponse;
import com.clinic.cms.doctor.entity.DoctorSpecialization;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorSpecializationMapper {

    DoctorSpecialization toEntity(
            DoctorSpecializationCreateRequest request);

    DoctorSpecializationResponse toResponse(
            DoctorSpecialization specialization
    );
}