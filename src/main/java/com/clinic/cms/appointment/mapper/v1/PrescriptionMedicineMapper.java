package com.clinic.cms.appointment.mapper.v1;

import com.clinic.cms.appointment.dto.v1.PrescriptionMedicineCreateRequest;
import com.clinic.cms.appointment.dto.v1.PrescriptionMedicineResponse;
import com.clinic.cms.appointment.dto.v1.PrescriptionMedicineUpdateRequest;
import com.clinic.cms.appointment.entity.PrescriptionMedicine;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PrescriptionMedicineMapper {

    @Mapping(target = "prescription", ignore = true)
    PrescriptionMedicine toEntity(PrescriptionMedicineCreateRequest request);

    @Mapping(target = "prescriptionId", source = "prescription.id")
    PrescriptionMedicineResponse toResponse(PrescriptionMedicine medicine);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "prescription", ignore = true)
    void updateEntityFromRequest(
            PrescriptionMedicineUpdateRequest request,
            @MappingTarget PrescriptionMedicine medicine
    );
}
