package com.clinic.cms.appointment.mapper.v1;

import com.clinic.cms.appointment.dto.v1.AppointmentSlotResponse;
import com.clinic.cms.appointment.entity.AppointmentSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentSlotMapper {

    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(
            target = "doctorName",
            expression = "java(appointmentSlot.getDoctor().getFirstName() + \" \" + appointmentSlot.getDoctor().getLastName())"
    )
    AppointmentSlotResponse toResponse(AppointmentSlot appointmentSlot);

    List<AppointmentSlotResponse> toResponseList(List<AppointmentSlot> appointmentSlots);
}