package com.github.mritunjayr.retirement_planning_system.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {
    
    public static double round(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
