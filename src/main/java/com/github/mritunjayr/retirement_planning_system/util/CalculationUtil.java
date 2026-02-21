package com.github.mritunjayr.retirement_planning_system.util;

public class CalculationUtil {
    
    public static double calculateCeiling(double amount) {
        return Math.ceil(amount / 100.0) * 100.0;
    }

    public static double calculateRemanent(double amount) {
        return calculateCeiling(amount) - amount;
    }

    public static double calculateCompoundInterest(double principal, double rate, int years) {
        return principal * Math.pow(1 + rate, years);
    }

    public static double adjustForInflation(double amount, double inflationRate, int years) {
        return amount / Math.pow(1 + inflationRate, years);
    }

    public static double calculateTaxBenefit(double income, double npsDeduction) {
        double taxWithoutDeduction = calculateTax(income);
        double taxWithDeduction = calculateTax(income - npsDeduction);
        return taxWithoutDeduction - taxWithDeduction;
    }

    private static double calculateTax(double income) {
        if (income <= 700000) return 0;
        if (income <= 1000000) return (income - 700000) * 0.10;
        if (income <= 1200000) return 30000 + (income - 1000000) * 0.15;
        if (income <= 1500000) return 60000 + (income - 1200000) * 0.20;
        return 120000 + (income - 1500000) * 0.30;
    }
}
