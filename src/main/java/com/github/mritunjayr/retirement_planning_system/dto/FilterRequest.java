package com.github.mritunjayr.retirement_planning_system.dto;

import lombok.Data;
import java.util.List;

@Data
public class FilterRequest {
    private List<QPeriod> q;
    private List<PPeriod> p;
    private List<Period> k;
    private List<ExpenseRequest> transactions;
}
