package com.tecboone.schedule_planner.controller;


import com.tecboone.schedule_planner.infrastructure.entity.Scheduling;
import com.tecboone.schedule_planner.service.SchedulingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/scheduling")
@RequiredArgsConstructor
public class SchedulingController {

    private final SchedulingService schedulingService;

    @PostMapping
    public ResponseEntity<Scheduling> saveScheduling(@RequestBody Scheduling scheduling) {
        return ResponseEntity.accepted().body(schedulingService.saveScheduling(scheduling));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteScheduling(@RequestParam String client,
                                                 @RequestParam LocalDateTime dateTimeScheduling){

        schedulingService.deleteScheduling(dateTimeScheduling, client);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Scheduling>> requestSchedulingDay(@RequestParam LocalDate date){
        return ResponseEntity.ok().body(schedulingService.requestScheduling(date));
    }

    @PutMapping
    public ResponseEntity<Scheduling> changeScheduling(@RequestBody Scheduling scheduling,
                                                       @RequestParam String client,
                                                       @RequestParam LocalDateTime dateTimeScheduling){
        return ResponseEntity.accepted().body(schedulingService.changeScheduling(scheduling,dateTimeScheduling,client));
    }
}
