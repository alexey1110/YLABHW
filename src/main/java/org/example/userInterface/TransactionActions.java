package org.example.userInterface;

import org.example.model.Goal;
import org.example.model.User;
import org.example.model.transactionEnum.Category;
import org.example.model.transactionEnum.TransactionType;
import org.example.service.GoalService;
import org.example.service.TransactionService;

import java.util.List;
import java.util.Scanner;

public class TransactionActions {
    public static void add(Scanner scanner, TransactionService transactionService, User currentUser, GoalService goalService) {
        System.out.print("Введите сумму транзакции: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Введите категорию:\n" +
                "   1 FOOD,\n" +
                "   2 TRANSPORT,\n" +
                "   3 HEALTH,\n" +
                "   4 SALARY,\n" +
                "   5 OTHER.\n");
        Category category = null;
        int numberCategory = scanner.nextInt();
        switch (numberCategory){
            case 1:
                category = Category.FOOD;
                break;
            case 2:
                category = Category.TRANSPORT;
                break;
            case 3:
                category = Category.HEALTH;
                break;
            case 4:
                category = Category.SALARY;
                break;
            case 5:
                category = Category.OTHER;
                break;
        }

        System.out.print("Введите описание: ");
        String description = scanner.nextLine();

        System.out.print("Введите тип:\n" +
                "    1 INCOME,\n" +
                "    2 EXPENSE.\n");
        TransactionType type = null;
        int numberType = scanner.nextInt();
        switch (numberType) {
            case 1:
                type = TransactionType.INCOME;
                break;
            case 2:
                type = TransactionType.EXPENSE;
                break;
        }

        System.out.print("Введите ID цели, если есть (или 0 если нет): \n");
        List<Goal> goals = goalService.viewGoals(currentUser.getId());
        for(Goal goal : goals){
            System.out.println(goal);
        }
        Long goalId = scanner.nextLong();
        if (goalId == 0) goalId = null;

        transactionService.addTransaction(currentUser.getId(), amount, category, description, type, goalId);
    }

    public static void edit (Scanner scanner, TransactionService transactionService, User currentUser) {
        System.out.print("Введите ID транзакции для редактирования: \n");
        transactionService.viewAllTransactions(currentUser.getId());
        long transactionId = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Введите новую сумму: ");
        double newAmount = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Введите новую категорию:");
        System.out.println("   1. FOOD");
        System.out.println("   2. TRANSPORT");
        System.out.println("   3. HEALTH");
        System.out.println("   4. SALARY");
        System.out.println("   5. OTHER");
        Category newCategory = null;
        int numberCategory = scanner.nextInt();
        scanner.nextLine();
        switch (numberCategory){
            case 1:
                newCategory = Category.FOOD;
                break;
            case 2:
                newCategory = Category.TRANSPORT;
                break;
            case 3:
                newCategory = Category.HEALTH;
                break;
            case 4:
                newCategory = Category.SALARY;
                break;
            case 5:
                newCategory = Category.OTHER;
                break;
            default:
                System.out.println("Неверная категория!");
                return;
        }

        System.out.print("Введите новое описание: ");
        String newDescription = scanner.nextLine();

        transactionService.editTransaction(currentUser.getId(), transactionId, newAmount, newCategory, newDescription);
    }

    public static void delete(Scanner scanner, TransactionService transactionService, User currentUser) {
        System.out.print("Введите ID транзакции для удаления: ");
        long transactionId = scanner.nextLong();
        scanner.nextLine();

        transactionService.deleteTransaction(currentUser.getId(), transactionId);
    }

    public static void viewAll(Scanner scanner, TransactionService transactionService, User currentUser) {
        System.out.println("Просмотр всех транзакций:");
        transactionService.viewAllTransactions(currentUser.getId());
    }
}
