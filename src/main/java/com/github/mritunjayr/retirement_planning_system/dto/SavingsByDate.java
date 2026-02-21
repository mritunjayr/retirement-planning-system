package com.github.mritunjayr.retirement_planning_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SavingsByDate {
    private String start;
    private String end;
    private double amount;
    private double profit;
    private double taxBenefit;
}
