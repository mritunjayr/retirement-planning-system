package com.github.mritunjayr.retirement_planning_system.service;

import com.github.mritunjayr.retirement_planning_system.dto.PerformanceResponse;
import org.springframework.stereotype.Service;


@Service
public class PerformanceService {

    private long lastRequestTime = System.currentTimeMillis();

    public void recordRequest() {
        lastRequestTime = System.currentTimeMillis();
    }

    public PerformanceResponse getPerformanceMetrics() {
        long currentTime = System.currentTimeMillis();
        long duration = currentTime - lastRequestTime;

        String timeStr = String.format("%d ms", duration);

        Runtime runtime = Runtime.getRuntime();
        double usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024.0 * 1024.0);
        String memory = String.format("%.2f", usedMemory);

        int threads = Thread.activeCount();

        return new PerformanceResponse(timeStr, memory, threads);
    }
}
