package com.github.mritunjayr.retirement_planning_system.dto;

import lombok.Data;
import java.util.List;

@Data
public class ValidatorRequest {
    private double wage;
    private List<Transaction> transactions;
}
