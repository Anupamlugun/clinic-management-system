package com.clinic.cms.patient.mapper.v1;

import com.clinic.cms.patient.dto.v1.*;
import com.clinic.cms.patient.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    Patient toEntity(PatientCreateRequest request);

    PatientResponse toResponse(Patient patient);
}