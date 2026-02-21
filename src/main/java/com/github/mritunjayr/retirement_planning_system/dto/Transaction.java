package com.github.mritunjayr.retirement_planning_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private LocalDateTime date;
    private double amount;
    private double ceiling;
    private double remanent;
}
