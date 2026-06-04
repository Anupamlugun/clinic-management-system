package com.clinic.cms.doctor.mapper;

import com.clinic.cms.doctor.dto.DoctorCreateRequest;
import com.clinic.cms.doctor.dto.DoctorResponse;
import com.clinic.cms.doctor.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = DoctorSpecializationMapper.class
)
public interface DoctorMapper {

    @Mapping(target = "specialization", ignore = true)
    Doctor toEntity(DoctorCreateRequest request);

    DoctorResponse toResponse(Doctor doctor);
}