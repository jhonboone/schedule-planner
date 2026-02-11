package com.tecboone.schedule_planner.infrastructure.repository;

import com.tecboone.schedule_planner.infrastructure.entity.Scheduling;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

    List<Scheduling> findByServiceAndDateTimeSchedulingBetween(
            String service,
            LocalDateTime dateTimeStart,
            LocalDateTime dateTimeEnd);

//    @Transactional
//    void deleteByDateTimeSchedulingAndClient(
//          LocalDateTime dateTimeScheduling,
//          String client);

    List<Scheduling> findByDateTimeSchedulingBetween(
            LocalDateTime dateTimeStart,
            LocalDateTime dateTimeEnd);

    Optional<Scheduling> findByDateTimeSchedulingAndClient(
            LocalDateTime dateTimeScheduling,
            String client);

    @Modifying
    @Query("DELETE FROM Scheduling s WHERE s.dateTimeScheduling = :dateTime AND s.client = :client")
    int deleteByDateTimeSchedulingAndClient(
            @Param("dateTime") LocalDateTime dateTimeScheduling,
            @Param("client") String client);
}


