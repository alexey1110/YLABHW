package org.example.userInterface;

import org.example.service.BudgetService;
import org.example.model.User;

import java.util.Scanner;

public class BudgetActions {

    public static void manage(Scanner scanner, BudgetService budgetService, User currentUser) {
        while (true) {
            System.out.println("\n--- Управление бюджетом ---");
            budgetService.showBudgetStatus(currentUser.getId());

            System.out.print("Хотите установить новый месячный бюджет? (y/n): ");
            String choice = scanner.nextLine();

            if ("y".equalsIgnoreCase(choice)) {
                System.out.print("Введите новый месячный бюджет: ");
                double limit = scanner.nextDouble();
                scanner.nextLine();

                budgetService.setMonthlyLimit(currentUser.getId(), limit);
                System.out.println("Новый месячный бюджет установлен!");
                break;
            } else if ("n".equalsIgnoreCase(choice)) {
                System.out.println("Установка нового бюджета отменена.");
                break;
            } else if ("exit".equalsIgnoreCase(choice)) {
                System.out.println("Выход из управления бюджетом.");
                break;
            } else {
                System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }
}