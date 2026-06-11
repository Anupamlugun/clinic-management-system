package com.clinic.cms.appointment.mapper.v1;

import com.clinic.cms.appointment.dto.v1.PrescriptionCreateRequest;
import com.clinic.cms.appointment.dto.v1.PrescriptionResponse;
import com.clinic.cms.appointment.dto.v1.PrescriptionUpdateRequest;
import com.clinic.cms.appointment.entity.Prescription;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

    @Mapping(target = "appointment", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "patient", ignore = true)
    Prescription toEntity(PrescriptionCreateRequest request);

    @Mapping(target = "appointmentId", source = "appointment.id")
    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(
            target = "doctorName",
            expression = "java(prescription.getDoctor() != null ? prescription.getDoctor().getFirstName() + \" \" + prescription.getDoctor().getLastName() : null)"
    )
    @Mapping(
            target = "patientName",
            expression = "java(prescription.getPatient() != null ? prescription.getPatient().getFirstName() + \" \" + prescription.getPatient().getLastName() : null)"
    )
    PrescriptionResponse toResponse(Prescription prescription);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "appointment", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "patient", ignore = true)
    void updateEntityFromRequest(
            PrescriptionUpdateRequest request,
            @MappingTarget Prescription prescription
    );
}