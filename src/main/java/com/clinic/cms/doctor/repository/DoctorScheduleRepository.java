package com.clinic.cms.doctor.repository;

import com.clinic.cms.common.enums.WeekDay;
import com.clinic.cms.doctor.entity.DoctorSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

    Optional<DoctorSchedule> findByDoctorIdAndDay(
            Long doctorId,
            WeekDay day);

    List<DoctorSchedule> findByDoctorId(Long doctorId);

    boolean existsByDoctorIdAndDay(
            Long doctorId,
            WeekDay day);

    @EntityGraph(attributePaths = "doctor")
    Page<DoctorSchedule> findAll(Pageable pageable);

    List<DoctorSchedule> findByDayAndActiveTrue(WeekDay day);

}