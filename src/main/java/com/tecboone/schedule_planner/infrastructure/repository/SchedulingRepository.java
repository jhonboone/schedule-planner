package com.tecboone.schedule_planner.infrastructure.repository;

import com.tecboone.schedule_planner.infrastructure.entity.Scheduling;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

    Scheduling findByServiceAndDateTimeSchedulingBetween(String service, LocalDateTime dateTimeStart,

                                                         LocalDateTime dateTimeEnd);
    @Transactional
    void deleteByDateTimeSchedulingAndClient(LocalDateTime dateTimeScheduling, String client);

    List<Scheduling> findByDateTimeSchedulingBetween(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd);

    Scheduling findByDateTimeSchedulingAndClient(LocalDateTime dateTimeScheduling, String client);
}


