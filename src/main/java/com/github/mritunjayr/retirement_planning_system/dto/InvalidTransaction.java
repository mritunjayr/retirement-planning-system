package com.github.mritunjayr.retirement_planning_system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class InvalidTransaction extends Transaction {
    private String message;

    public InvalidTransaction(LocalDateTime date, double amount, double ceiling, double remanent, String message) {
        super(date, amount, ceiling, remanent);
        this.message = message;
    }
}
