package com.clinic.cms.appointment.scheduler;

import com.clinic.cms.appointment.entity.AppointmentSlot;
import com.clinic.cms.appointment.repository.AppointmentSlotRepository;
import com.clinic.cms.common.enums.WeekDay;
import com.clinic.cms.doctor.entity.DoctorSchedule;
import com.clinic.cms.doctor.repository.DoctorScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppointmentSlotScheduler {

    private final AppointmentSlotRepository slotRepository;
    private final DoctorScheduleRepository scheduleRepository;

    private static final int SLOT_DURATION_MINUTES = 30;

    @Scheduled(cron = "0 0 0 * * *")
//    @Scheduled(cron = "*/5 * * * * *")
    public void generateSlotsForToday() {

        LocalDate today = LocalDate.now();

        WeekDay day = WeekDay.valueOf(
                DayOfWeek.from(today).name()
        );

        List<DoctorSchedule> schedules =
                scheduleRepository.findByDayAndActiveTrue(day);

        List<AppointmentSlot> slots = new ArrayList<>();

        for (DoctorSchedule schedule : schedules) {

            Long doctorId = schedule.getDoctor().getId();

            if (slotRepository.existsByDoctorIdAndSlotDate(
                    doctorId,
                    today
            )) {
                continue;
            }

            LocalTime start = schedule.getStartTime();
            LocalTime end = schedule.getEndTime();

            while (start.plusMinutes(SLOT_DURATION_MINUTES)
                    .compareTo(end) <= 0) {

                slots.add(
                        AppointmentSlot.builder()
                                .doctor(schedule.getDoctor())
                                .slotDate(today)
                                .startTime(start)
                                .endTime(start.plusMinutes(
                                        SLOT_DURATION_MINUTES
                                ))
                                .booked(false)
                                .active(true)
                                .build()
                );

                start = start.plusMinutes(
                        SLOT_DURATION_MINUTES
                );
            }
        }

        slotRepository.saveAll(slots);

        log.info(
                "Generated {} appointment slots for {}",
                slots.size(),
                today
        );
    }
}