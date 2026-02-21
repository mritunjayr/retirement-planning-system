package com.github.mritunjayr.retirement_planning_system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PPeriod extends Period {
    private double extra;
}
