package com.github.mritunjayr.retirement_planning_system.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime parse(String date) {
        return LocalDateTime.parse(date, FORMATTER);
    }

    public static boolean isInRange(String date, String start, String end) {
        LocalDateTime dt = parse(date);
        LocalDateTime startDt = parse(start);
        LocalDateTime endDt = parse(end);
        return !dt.isBefore(startDt) && !dt.isAfter(endDt);
    }
}
