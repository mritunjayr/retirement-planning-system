package com.github.mritunjayr.retirement_planning_system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class FilteredTransaction extends Transaction {
    private Boolean inKPeriod;

    public FilteredTransaction(LocalDateTime date, double amount, double ceiling, double remanent, Boolean inKPeriod) {
        super(date, amount, ceiling, remanent);
        this.inKPeriod = inKPeriod;
    }
}
