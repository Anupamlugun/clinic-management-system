package com.clinic.cms.doctor.mapper.v1;

import com.clinic.cms.doctor.dto.v1.DoctorCreateRequest;
import com.clinic.cms.doctor.dto.v1.DoctorResponse;
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