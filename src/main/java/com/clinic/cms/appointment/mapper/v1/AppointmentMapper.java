package com.clinic.cms.appointment.mapper.v1;

import com.clinic.cms.appointment.dto.v1.AppointmentCreateRequest;
import com.clinic.cms.appointment.dto.v1.AppointmentResponse;
import com.clinic.cms.appointment.dto.v1.AppointmentUpdateRequest;
import com.clinic.cms.appointment.entity.Appointment;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AppointmentMapper {

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "slot", ignore = true)
    @Mapping(target = "appointmentDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "parentAppointment", ignore = true)
    Appointment toEntity(AppointmentCreateRequest request);

    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "slotId", source = "slot.id")
    @Mapping(
            target = "slotTime",
            expression = "java(appointment.getSlot() != null ? appointment.getSlot().getStartTime() + \" - \" + appointment.getSlot().getEndTime() : null)"
    )
    @Mapping(target = "patientName",
            expression = "java(appointment.getPatient() != null ? appointment.getPatient().getFirstName() + \" \" + appointment.getPatient().getLastName() : null)")
    @Mapping(
            target = "doctorName",
            expression = "java(appointment.getDoctor() != null ? appointment.getDoctor().getFirstName() + \" \" + appointment.getDoctor().getLastName() : null)"
    )
    @Mapping(target = "parentAppointmentId", source = "parentAppointment.id")
    AppointmentResponse toResponse(Appointment appointment);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "slot", ignore = true)
    @Mapping(target = "appointmentDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "parentAppointment", ignore = true)
    void updateEntityFromRequest(
            AppointmentUpdateRequest request,
            @MappingTarget Appointment appointment
    );
}