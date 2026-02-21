package com.github.mritunjayr.retirement_planning_system.dto;

import lombok.Data;

@Data
public class ExpenseRequest {
    private String date;
    private double amount;
}
