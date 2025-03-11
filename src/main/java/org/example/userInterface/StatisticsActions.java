package org.example.userInterface;

import org.example.service.TransactionService;
import org.example.model.User;
import org.example.model.transactionEnum.Category;

import java.util.Scanner;

public class StatisticsActions {

    public static void showStatistics(TransactionService transactionService, User currentUser, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Статистика и аналитика ---");
            System.out.println("1. Подсчитать текущий баланс");
            System.out.println("2. Рассчитать доходы и расходы за период");
            System.out.println("3. Анализ расходов по категориям");
            System.out.println("4. Формирование отчёта о финансовом состоянии");
            System.out.println("5. Назад");
            System.out.print("Выберите опцию: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline character

            switch (choice) {
                case 1:
                    showBalance(transactionService, currentUser);
                    break;
                case 2:
                    showIncomeAndExpensesForPeriod(transactionService, currentUser, scanner);
                    break;
                case 3:
                    showExpensesByCategory(transactionService, currentUser);
                    break;
                case 4:
                    generateFinancialReport(transactionService, currentUser);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }

    private static void showBalance(TransactionService transactionService, User currentUser) {
        double balance = transactionService.calculateBalance(currentUser.getId());
        System.out.println("Ваш текущий баланс: " + balance);
    }

    private static void showIncomeAndExpensesForPeriod(TransactionService transactionService, User currentUser, Scanner scanner) {
        System.out.print("Введите начальную дату (формат: YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.print("Введите конечную дату (формат: YYYY-MM-DD): ");
        String endDate = scanner.nextLine();

        double totalIncome = transactionService.calculateIncomeForPeriod(currentUser.getId(), startDate, endDate);
        double totalExpense = transactionService.calculateExpenseForPeriod(currentUser.getId(), startDate, endDate);

        System.out.println("Суммарный доход за период: " + totalIncome);
        System.out.println("Суммарные расходы за период: " + totalExpense);
    }

    private static void showExpensesByCategory(TransactionService transactionService, User currentUser) {
        System.out.println("\n--- Расходы по категориям ---");
        for (Category category : Category.values()) {
            double totalExpense = transactionService.calculateExpensesByCategory(currentUser.getId(), category);
            System.out.println(category + ": " + totalExpense);
        }
    }

    private static void generateFinancialReport(TransactionService transactionService, User currentUser) {
        double balance = transactionService.calculateBalance(currentUser.getId());
        double totalIncome = transactionService.calculateTotalIncome(currentUser.getId());
        double totalExpense = transactionService.calculateTotalExpense(currentUser.getId());

        System.out.println("\n--- Финансовый отчёт ---");
        System.out.println("Текущий баланс: " + balance);
        System.out.println("Общий доход: " + totalIncome);
        System.out.println("Общие расходы: " + totalExpense);

        System.out.println("\nАнализ по категориям:");
        for (Category category : Category.values()) {
            double totalCategoryExpense = transactionService.calculateExpensesByCategory(currentUser.getId(), category);
            System.out.println(category + " - " + totalCategoryExpense);
        }
    }
}
