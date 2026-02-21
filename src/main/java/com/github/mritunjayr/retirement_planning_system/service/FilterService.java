package com.github.mritunjayr.retirement_planning_system.service;

import com.github.mritunjayr.retirement_planning_system.dto.*;
import com.github.mritunjayr.retirement_planning_system.util.CalculationUtil;
import com.github.mritunjayr.retirement_planning_system.util.DateUtil;
import com.github.mritunjayr.retirement_planning_system.util.NumberUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class FilterService {

    public FilterResponse filterTransactions(FilterRequest request) {
        List<FilteredTransaction> valid = new ArrayList<>();
        List<Object> invalid = new ArrayList<>();

        for (ExpenseRequest expense : request.getTransactions()) {
            if (expense.getAmount() < 0) {
                Map<String, Object> invalidTx = new HashMap<>();
                invalidTx.put("date", expense.getDate());
                invalidTx.put("amount", expense.getAmount());
                invalidTx.put("ceiling", 0.0);
                invalidTx.put("remanent", 0.0);
                invalidTx.put("message", "Negative amounts are not allowed");
                invalid.add(invalidTx);
                continue;
            }

            double ceiling = NumberUtil.round(CalculationUtil.calculateCeiling(expense.getAmount()));
            double remanent = NumberUtil.round(CalculationUtil.calculateRemanent(expense.getAmount()));

            // Apply q periods
            remanent = applyQPeriods(expense.getDate(), remanent, request.getQ());

            // Apply p periods
            remanent = applyPPeriods(expense.getDate(), remanent, request.getP());

            // Check if in any k period
            boolean inAPeriod = isInAnyKPeriod(expense.getDate(), request.getK());

            if (inAPeriod) {
                valid.add(new FilteredTransaction(expense.getDate(), expense.getAmount(), ceiling,
                        NumberUtil.round(remanent), true));
            } else {
                Map<String, Object> invalidTx = new HashMap<>();
                invalidTx.put("date", expense.getDate());
                invalidTx.put("amount", expense.getAmount());
                invalidTx.put("ceiling", ceiling);
                invalidTx.put("remanent", NumberUtil.round(remanent));
                invalidTx.put("inAPeriod", false);
                invalid.add(invalidTx);
            }
        }

        return new FilterResponse(valid, invalid);
    }

    private double applyQPeriods(LocalDateTime date, double remanent, List<QPeriod> qPeriods) {
        if (qPeriods == null)
            return remanent;

        QPeriod matchingPeriod = null;
        LocalDateTime latestStart = null;

        for (int i = 0; i < qPeriods.size(); i++) {
            QPeriod q = qPeriods.get(i);
            if (DateUtil.isInRange(date, q.getStart(), q.getEnd())) {
                LocalDateTime currentStart = q.getStart();
                if (matchingPeriod == null || currentStart.isAfter(latestStart) ||
                        (currentStart.equals(latestStart) && matchingPeriod != q)) {
                    matchingPeriod = q;
                    latestStart = currentStart;
                }
            }
        }

        return matchingPeriod != null ? matchingPeriod.getFixed() : remanent;
    }

    private double applyPPeriods(LocalDateTime date, double remanent, List<PPeriod> pPeriods) {
        if (pPeriods == null)
            return remanent;

        for (PPeriod p : pPeriods) {
            if (DateUtil.isInRange(date, p.getStart(), p.getEnd())) {
                remanent += p.getExtra();
            }
        }
        return remanent;
    }

    private boolean isInAnyKPeriod(LocalDateTime date, List<Period> kPeriods) {
        if (kPeriods == null || kPeriods.isEmpty())
            return true;

        for (Period k : kPeriods) {
            if (DateUtil.isInRange(date, k.getStart(), k.getEnd())) {
                return true;
            }
        }
        return false;
    }
}
