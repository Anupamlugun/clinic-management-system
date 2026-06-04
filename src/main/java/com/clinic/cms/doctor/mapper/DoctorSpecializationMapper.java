package com.clinic.cms.doctor.mapper;

import com.clinic.cms.doctor.dto.DoctorSpecializationCreateRequest;
import com.clinic.cms.doctor.dto.DoctorSpecializationResponse;
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