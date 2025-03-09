package org.example.service;

import org.example.model.Transaction;
import org.example.model.transactionEnum.TransactionType;
import org.example.model.transactionEnum.Category;
import org.example.repository.TransactionRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final GoalService goalService;
    private final BudgetService budgetService;

    public TransactionService(TransactionRepository transactionRepository, GoalService goalService, BudgetService budgetService) {
        this.transactionRepository = transactionRepository;
        this.goalService = goalService;
        this.budgetService = budgetService;
    }

    public void addTransaction(long userId, double amount, Category category, String description, TransactionType type, Long goalId) {
        Transaction transaction = new Transaction(userId, amount, LocalDate.now(), category, description, type, goalId);
        transactionRepository.save(transaction);

        if (type == TransactionType.INCOME && goalId != null) {
            double percentToSave = 0.1;
            double amountToSave = amount * percentToSave;

            goalService.addSavingsToGoal(goalId, amountToSave);
        }

        if (type == TransactionType.EXPENSE) {
            if (budgetService.isBudgetExceeded(userId, amount)) {
                System.out.println("Warning: Budget exceeded with this expense.");
            }
            budgetService.addExpense(userId, amount);
        }
    }

    public void editTransaction(long userId, long transactionId, double newAmount, Category newCategory, String newDescription) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(transactionId);
        if (transactionOpt.isPresent() && transactionOpt.get().getUserId() == userId) {
            Transaction transaction = transactionOpt.get();

            if (transaction.getType() == TransactionType.EXPENSE) {
                budgetService.addExpense(userId, newAmount - transaction.getAmount());
            }
            transaction.setAmount(newAmount);
            transaction.setCategory(newCategory);
            transaction.setDescription(newDescription);
            transactionRepository.update(transactionId, transaction);
            System.out.println("Transaction updated successfully.");
        } else {
            System.out.println("Transaction not found or does not belong to the user.");
        }
    }

    public void deleteTransaction(long userId, long transactionId) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(transactionId);
        if (transactionOpt.isPresent() && transactionOpt.get().getUserId() == userId) {
            Transaction transaction = transactionOpt.get();
            if (transaction.getGoalId() != null) {
                System.out.println("Transaction is linked to a goal, it can't be deleted directly.");
                return;
            }

            if (transaction.getType() == TransactionType.EXPENSE) {
                budgetService.addExpense(userId, -transaction.getAmount());
            }
            if (transactionRepository.delete(transactionId)) {
                System.out.println("Transaction deleted successfully.");
            } else {
                System.out.println("Transaction not found.");
            }
        } else {
            System.out.println("Transaction not found or does not belong to the user.");
        }
    }

    public void viewAllTransactions(long userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        if (transactions.isEmpty()) {
            System.out.println("No transactions available for user.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction.getId() + ": " + transaction.getAmount() + " " + transaction.getType() + " (" + transaction.getCategory() + ")");
            }
        }
    }

    public double calculateBalance(long userId) {
        double totalIncome = transactionRepository.findByUserId(userId).stream()
                .filter(transaction -> transaction.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpense = transactionRepository.findByUserId(userId).stream()
                .filter(transaction -> transaction.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();

        return totalIncome - totalExpense;
    }

    public double calculateIncomeForPeriod(long userId, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return transactionRepository.findByUserId(userId).stream()
                .filter(transaction -> (transaction.getDate().isEqual(start) || transaction.getDate().isAfter(start)) &&
                        (transaction.getDate().isEqual(end) || transaction.getDate().isBefore(end)) &&
                        transaction.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double calculateExpenseForPeriod(long userId, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return transactionRepository.findByUserId(userId).stream()
                .filter(transaction -> (transaction.getDate().isEqual(start) || transaction.getDate().isAfter(start)) &&
                        (transaction.getDate().isEqual(end) || transaction.getDate().isBefore(end)) &&
                        transaction.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double calculateExpensesByCategory(long userId, Category category) {
        return transactionRepository.findByUserId(userId).stream()
                .filter(transaction -> transaction.getCategory() == category && transaction.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }


    public double calculateTotalIncome(long userId) {
        return transactionRepository.findByUserId(userId).stream()
                .filter(transaction -> transaction.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double calculateTotalExpense(long userId) {
        return transactionRepository.findByUserId(userId).stream()
                .filter(transaction -> transaction.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}