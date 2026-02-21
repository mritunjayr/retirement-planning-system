package com.github.mritunjayr.retirement_planning_system.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExpenseRequest {
    
    private LocalDateTime date;
    private double amount;
}
