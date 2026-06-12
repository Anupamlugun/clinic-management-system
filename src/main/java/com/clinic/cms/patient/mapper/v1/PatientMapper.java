package com.clinic.cms.patient.mapper.v1;

import com.clinic.cms.patient.dto.v1.*;
import com.clinic.cms.patient.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "user", ignore = true)
    Patient toEntity(PatientCreateRequest request);

    PatientResponse toResponse(Patient patient);
}