package com.github.mritunjayr.retirement_planning_system.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Period {
    private LocalDateTime start;
    private LocalDateTime end;
}
