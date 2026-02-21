package com.github.mritunjayr.retirement_planning_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SavingsByDate {
    private LocalDateTime start;
    private LocalDateTime end;
    private double amount;
    private double profit;
    private double taxBenefit;
}
