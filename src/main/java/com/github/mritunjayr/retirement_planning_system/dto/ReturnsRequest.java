package com.github.mritunjayr.retirement_planning_system.dto;

import lombok.Data;
import java.util.List;

@Data
public class ReturnsRequest {
    private int age;
    private double wage;
    private double inflation;
    private List<QPeriod> q;
    private List<PPeriod> p;
    private List<Period> k;
    private List<ExpenseRequest> transactions;
}
