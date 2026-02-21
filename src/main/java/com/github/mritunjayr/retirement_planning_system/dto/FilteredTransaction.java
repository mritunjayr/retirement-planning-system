package com.github.mritunjayr.retirement_planning_system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FilteredTransaction extends Transaction {
    private Boolean inAPeriod;

    public FilteredTransaction(String date, double amount, double ceiling, double remanent, Boolean inAPeriod) {
        super(date, amount, ceiling, remanent);
        this.inAPeriod = inAPeriod;
    }
}
