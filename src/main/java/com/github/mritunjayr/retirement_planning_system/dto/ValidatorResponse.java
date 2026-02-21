package com.github.mritunjayr.retirement_planning_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ValidatorResponse {
    private List<Transaction> valid;
    private List<InvalidTransaction> invalid;
}
