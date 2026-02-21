package com.github.mritunjayr.retirement_planning_system.service;

import com.github.mritunjayr.retirement_planning_system.dto.PerformanceResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PerformanceService {

    private long lastRequestTime = System.currentTimeMillis();

    public void recordRequest() {
        lastRequestTime = System.currentTimeMillis();
    }

    public PerformanceResponse getPerformanceMetrics() {
        long currentTime = System.currentTimeMillis();
        long duration = currentTime - lastRequestTime;

        LocalDateTime time = LocalDateTime.of(1970, 1, 1, 0, 0, 0)
            .plusNanos(duration * 1_000_000);
        String timeStr = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        Runtime runtime = Runtime.getRuntime();
        double usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024.0 * 1024.0);
        String memory = String.format("%.2f", usedMemory);

        int threads = Thread.activeCount();

        return new PerformanceResponse(timeStr, memory, threads);
    }
}
