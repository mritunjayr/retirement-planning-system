package com.github.mritunjayr.retirement_planning_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class FilterResponse {
    private List<FilteredTransaction> valid;
    private List<Object> invalid;
}
