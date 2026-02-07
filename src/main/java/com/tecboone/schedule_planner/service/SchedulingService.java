package com.tecboone.schedule_planner.service;

import com.tecboone.schedule_planner.infrastructure.entity.Scheduling;
import com.tecboone.schedule_planner.infrastructure.repository.SchedulingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final SchedulingRepository schedulingRepository;

    public Scheduling saveScheduling(Scheduling scheduling) {
        LocalDateTime timeScheduling = scheduling.getDateTimeScheduling();
        LocalDateTime endTime = scheduling.getDateTimeScheduling().plusHours(1);

        Scheduling scheduled = schedulingRepository.findByServiceAndDateTimeSchedulingBetween(scheduling.getService(),
                timeScheduling, endTime);

        if (Objects.nonNull(scheduled)) {
            throw new RuntimeException("Schedule already filled");
        }
        return schedulingRepository.save(scheduling);

    }

    public void deleteScheduling(LocalDateTime dataTimeScheduling , String client) {

        schedulingRepository.deleteByDateTimeSchedulingAndClient(dataTimeScheduling, client);
    }

    public List<Scheduling> requestScheduling(LocalDate date){
        LocalDateTime firstHourDay = date.atStartOfDay();
        LocalDateTime endHourDay = date.atTime(23, 59, 59);

        return schedulingRepository.findByDateTimeSchedulingBetween(firstHourDay, endHourDay);
    }

    public Scheduling changeScheduling(Scheduling scheduling, LocalDateTime dateTimeScheduling, String client){
        Scheduling scheduling1 = schedulingRepository.findByDateTimeSchedulingAndClient(dateTimeScheduling, client);

        if (Objects.isNull(scheduling)) {
            throw new RuntimeException("Schedule is not filled");
        }
        scheduling.setId((scheduling1.getId()));
        return schedulingRepository.save(scheduling);
    }
}
