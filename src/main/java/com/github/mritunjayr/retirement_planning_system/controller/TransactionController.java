package com.github.mritunjayr.retirement_planning_system.controller;

import com.github.mritunjayr.retirement_planning_system.dto.*;
import com.github.mritunjayr.retirement_planning_system.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/challenge/v1")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final FilterService filterService;
    private final ReturnsService returnsService;
    private final PerformanceService performanceService;

    @PostMapping("/transactions:parse")
    public List<Transaction> parseTransactions(@RequestBody List<ExpenseRequest> expenses) {
        performanceService.recordRequest();
        return transactionService.parseTransactions(expenses);
    }

    @PostMapping("/transactions:validator")
    public ValidatorResponse validateTransactions(@RequestBody ValidatorRequest request) {
        performanceService.recordRequest();
        return transactionService.validateTransactions(request.getWage(), request.getTransactions());
    }

    @PostMapping("/transactions:filter")
    public FilterResponse filterTransactions(@RequestBody FilterRequest request) {
        performanceService.recordRequest();
        return filterService.filterTransactions(request);
    }

    @PostMapping("/returns:nps")
    public ReturnsResponse calculateNPSReturns(@RequestBody ReturnsRequest request) {
        performanceService.recordRequest();
        return returnsService.calculateReturns(request, true);
    }

    @PostMapping("/returns:index")
    public ReturnsResponse calculateIndexReturns(@RequestBody ReturnsRequest request) {
        performanceService.recordRequest();
        return returnsService.calculateReturns(request, false);
    }

    @GetMapping("/performance")
    public PerformanceResponse getPerformance() {
        return performanceService.getPerformanceMetrics();
    }
}
