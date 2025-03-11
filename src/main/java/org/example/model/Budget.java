package org.example.model;

public class Budget {
    private long userId;
    private double monthlyLimit;
    private double currentSpending;

    public Budget(long userId, double monthlyLimit, double currentSpending) {
        this.userId = userId;
        this.monthlyLimit = monthlyLimit;
        this.currentSpending = currentSpending;
    }

    public long getUserId() {
        return userId;
    }

    public double getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(double monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public double getCurrentSpending() {
        return currentSpending;
    }

    public void addExpense(double amount) {
        if (amount > 0) {
            currentSpending += amount;
        }
    }

    public void resetSpending() {
        currentSpending = 0;
    }

    public boolean isExceeded() {
        return currentSpending > monthlyLimit;
    }

    public double getRemainingBudget() {
        return monthlyLimit - currentSpending;
    }


}
