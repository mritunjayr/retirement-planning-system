package com.github.mritunjayr.retirement_planning_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ReturnsResponse {
    private double totalTransactionAmount;
    private double totalCeiling;
    private List<SavingsByDate> savingsByDates;
}
