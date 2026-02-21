package com.github.mritunjayr.retirement_planning_system.service;

import com.github.mritunjayr.retirement_planning_system.dto.*;
import com.github.mritunjayr.retirement_planning_system.util.CalculationUtil;
import com.github.mritunjayr.retirement_planning_system.util.NumberUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TransactionService {

    public List<Transaction> parseTransactions(List<ExpenseRequest> expenses) {
        List<Transaction> transactions = new ArrayList<>();
        for (ExpenseRequest expense : expenses) {
            double ceiling = NumberUtil.round(CalculationUtil.calculateCeiling(expense.getAmount()));
            double remanent = NumberUtil.round(CalculationUtil.calculateRemanent(expense.getAmount()));
            transactions.add(new Transaction(expense.getDate(), expense.getAmount(), ceiling, remanent));
        }
        return transactions;
    }

    public ValidatorResponse validateTransactions(double wage, List<Transaction> transactions) {
        List<Transaction> valid = new ArrayList<>();
        List<InvalidTransaction> invalid = new ArrayList<>();
        Set<LocalDateTime> seenDates = new HashSet<>();

        for (Transaction t : transactions) {
            if (t.getAmount() < 0) {
                invalid.add(new InvalidTransaction(t.getDate(), t.getAmount(), t.getCeiling(), 
                    t.getRemanent(), "Negative amounts are not allowed"));
            } else if (seenDates.contains(t.getDate())) {
                invalid.add(new InvalidTransaction(t.getDate(), t.getAmount(), t.getCeiling(), 
                    t.getRemanent(), "Duplicate transaction"));
            } else {
                valid.add(t);
                seenDates.add(t.getDate());
            }
        }
        return new ValidatorResponse(valid, invalid);
    }
}
