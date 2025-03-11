package org.example.service;

import org.example.model.Budget;
import org.example.repository.BudgetRepository;

import java.util.Optional;

public class BudgetService {
    private BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }
    public boolean isBudgetExceeded(long userId, double amount) {
        Optional<Budget> budgetOpt = budgetRepository.findByUserId(userId);
        if (budgetOpt.isPresent()) {
            Budget budget = budgetOpt.get();
            return budget.getCurrentSpending() + amount > budget.getMonthlyLimit();
        }
        return false;
    }
    public void setMonthlyLimit(long userId, double limit) {
        Optional<Budget> budgetOpt = budgetRepository.findByUserId(userId);
        if (budgetOpt.isPresent()) {
            Budget budget = budgetOpt.get();
            budget.setMonthlyLimit(limit);
            budgetRepository.update(budget);
            System.out.println("Бюджет обновлен.");
        } else {
            Budget newBudget = new Budget(userId, limit, 0);
            budgetRepository.save(newBudget);
            System.out.println("Бюджет установлен.");
        }
    }

    public void addExpense(long userId, double amount) {
        Optional<Budget> budgetOpt = budgetRepository.findByUserId(userId);
        if (budgetOpt.isPresent()) {
            Budget budget = budgetOpt.get();
            budget.addExpense(amount);
            budgetRepository.update(budget);
            System.out.println("Расход добавлен.");
            if (budget.isExceeded()) {
                System.out.println("Бюджет превышен!");
            }
        } else {
            System.out.println("У пользователя нет установленного бюджета.");
        }
    }

    public void showBudgetStatus(long userId) {
        Optional<Budget> budgetOpt = budgetRepository.findByUserId(userId);
        if (budgetOpt.isPresent()) {
            Budget budget = budgetOpt.get();
            System.out.println("Текущие расходы: " + budget.getCurrentSpending());
            System.out.println("Лимит: " + budget.getMonthlyLimit());
            System.out.println("Остаток: " + budget.getRemainingBudget());
        } else {
            System.out.println("У пользователя нет установленного бюджета.");
        }
    }
}
