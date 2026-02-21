package com.github.mritunjayr.retirement_planning_system.service;

import com.github.mritunjayr.retirement_planning_system.dto.*;
import com.github.mritunjayr.retirement_planning_system.util.CalculationUtil;
import com.github.mritunjayr.retirement_planning_system.util.DateUtil;
import com.github.mritunjayr.retirement_planning_system.util.NumberUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReturnsService {

    public ReturnsResponse calculateReturns(ReturnsRequest request, boolean isNPS) {
        double rate = isNPS ? 0.0711 : 0.1449;
        int years = request.getAge() < 60 ? 60 - request.getAge() : 5;
        double annualIncome = request.getWage() * 12;

        List<Transaction> transactions = new ArrayList<>();
        Set<LocalDateTime> seenDates = new HashSet<>();
        
        for (ExpenseRequest expense : request.getTransactions()) {
            if (expense.getAmount() < 0 || seenDates.contains(expense.getDate())) continue;
            seenDates.add(expense.getDate());
            
            double ceiling = CalculationUtil.calculateCeiling(expense.getAmount());
            double remanent = CalculationUtil.calculateRemanent(expense.getAmount());

            // Apply q periods
            remanent = applyQPeriods(expense.getDate(), remanent, request.getQ());

            // Apply p periods
            remanent = applyPPeriods(expense.getDate(), remanent, request.getP());

            transactions.add(new Transaction(expense.getDate(), expense.getAmount(), ceiling, remanent));
        }

        double totalAmount = transactions.stream().mapToDouble(Transaction::getAmount).sum();
        double totalCeiling = transactions.stream().mapToDouble(Transaction::getCeiling).sum();

        List<SavingsByDate> savingsByDates = new ArrayList<>();

        for (Period k : request.getK()) {
            double amount = 0;
            for (Transaction t : transactions) {
                if (DateUtil.isInRange(t.getDate(), k.getStart(), k.getEnd())) {
                    amount += t.getRemanent();
                }
            }

            double finalValue = CalculationUtil.calculateCompoundInterest(amount, rate, years);
            double realValue = CalculationUtil.adjustForInflation(finalValue, request.getInflation() / 100.0, years);
            double profit = realValue - amount;

            double taxBenefit = 0;
            if (isNPS) {
                double npsDeduction = Math.min(amount, Math.min(annualIncome * 0.10, 200000));
                taxBenefit = CalculationUtil.calculateTaxBenefit(annualIncome, npsDeduction);
            }

            savingsByDates.add(new SavingsByDate(k.getStart(), k.getEnd(), 
                NumberUtil.round(amount), NumberUtil.round(profit), NumberUtil.round(taxBenefit)));
        }

        return new ReturnsResponse(NumberUtil.round(totalAmount), NumberUtil.round(totalCeiling), savingsByDates);
    }

    private double applyQPeriods(LocalDateTime date, double remanent, List<QPeriod> qPeriods) {
        if (qPeriods == null) return remanent;

        QPeriod matchingPeriod = null;
        for (QPeriod q : qPeriods) {
            if (DateUtil.isInRange(date, q.getStart(), q.getEnd())) {
                if (matchingPeriod == null || 
                    q.getStart().isAfter(matchingPeriod.getStart())) {
                    matchingPeriod = q;
                }
            }
        }

        return matchingPeriod != null ? matchingPeriod.getFixed() : remanent;
    }

    private double applyPPeriods(LocalDateTime date, double remanent, List<PPeriod> pPeriods) {
        if (pPeriods == null) return remanent;

        for (PPeriod p : pPeriods) {
            if (DateUtil.isInRange(date, p.getStart(), p.getEnd())) {
                remanent += p.getExtra();
            }
        }
        return remanent;
    }
}
