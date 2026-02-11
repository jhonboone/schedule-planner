package com.tecboone.schedule_planner.infrastructure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "scheduling")
public class Scheduling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The service is mandatory")
    @Size(max = 100, message = "Service name is too long")
    private String service;

    @NotBlank(message = "The professional is mandatory")
    private String professional;

    @NotNull(message = "The date is mandatory")
    @Future(message = "The date must be in the future")
    private LocalDateTime dateTimeScheduling;

    @NotBlank(message = "The client is mandatory")
    private String client;

    @Pattern(regexp = "^\\([0-9]{2}\\) [0-9]{4,5}-[0-9]{4}$",
            message = "Invalid phone number. Please use: ((DD) NNNNN-NNNN")
    private String clientPhone;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateInsert;

    @PrePersist
    protected void onCreate() {
        dateInsert = LocalDateTime.now();
    }
}
