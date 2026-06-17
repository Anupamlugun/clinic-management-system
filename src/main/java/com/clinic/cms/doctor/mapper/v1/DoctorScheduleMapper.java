package com.clinic.cms.doctor.mapper.v1;

import com.clinic.cms.doctor.dto.v1.DoctorScheduleCreateRequest;
import com.clinic.cms.doctor.dto.v1.DoctorScheduleResponse;
import com.clinic.cms.doctor.entity.Doctor;
import com.clinic.cms.doctor.entity.DoctorSchedule;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring"
)
public interface DoctorScheduleMapper {

    @Mapping(target = "doctor", source = "doctor")
    @Mapping(target = "active", ignore = true)
    DoctorSchedule toEntity(
            DoctorScheduleCreateRequest request,
            Doctor doctor
    );

    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(
            target = "doctorName",
            expression = "java(schedule.getDoctor().getFirstName() + \" \" + schedule.getDoctor().getLastName())"
    )
    DoctorScheduleResponse toResponse(DoctorSchedule schedule);

}