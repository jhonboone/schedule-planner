package com.tecboone.schedule_planner.service;

import com.tecboone.schedule_planner.exception.SchedulingAlreadyExistsException;
import com.tecboone.schedule_planner.exception.SchedulingNotFoundException;
import com.tecboone.schedule_planner.infrastructure.entity.Scheduling;
import com.tecboone.schedule_planner.infrastructure.repository.SchedulingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final SchedulingRepository schedulingRepository;

    public Scheduling saveScheduling(Scheduling scheduling) {
        LocalDateTime timeScheduling = scheduling.getDateTimeScheduling();
        LocalDateTime endTime = scheduling.getDateTimeScheduling().plusHours(1);

        List<Scheduling> scheduled = schedulingRepository.findByServiceAndDateTimeSchedulingBetween(
                scheduling.getService(),
                timeScheduling,
                endTime);

       if (!scheduled.isEmpty()) {
           throw new SchedulingAlreadyExistsException("Schedule already filled for the service");
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

    public Scheduling changeScheduling(
            Scheduling scheduling,
            LocalDateTime dateTimeScheduling,
            String client
    ){

        Scheduling existingScheduling = schedulingRepository
                .findByDateTimeSchedulingAndClient(dateTimeScheduling, client)
                .orElseThrow(() -> new SchedulingNotFoundException(
                        "No appointment found for the customer" + client + " on that date: " + dateTimeScheduling
                ));

     scheduling.setId(existingScheduling.getId());
     return schedulingRepository.save(scheduling);
    }
}
