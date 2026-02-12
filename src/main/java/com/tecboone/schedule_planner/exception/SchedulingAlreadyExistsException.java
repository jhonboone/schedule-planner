package com.tecboone.schedule_planner.exception;

public class SchedulingAlreadyExistsException extends RuntimeException
{
    public SchedulingAlreadyExistsException(String message) {
        super(message);
    }
}
