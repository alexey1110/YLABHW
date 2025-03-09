package org.example.userInterface;


import org.example.model.Goal;
import org.example.service.GoalService;
import org.example.model.User;

import java.util.List;
import java.util.Scanner;

public class GoalActions {

    public static void manage(Scanner scanner, GoalService goalService, User currentUser) {
        while (true) {
            System.out.println("\n--- Управление целями ---");
            System.out.println("1. Добавить цель");
            System.out.println("2. Просмотреть цели");
            System.out.println("3. Удалить цель");
            System.out.println("4. Назад");
            System.out.print("Выберите опцию: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addGoal(scanner, goalService, currentUser);
                    break;
                case 2:
                    viewGoals(goalService, currentUser);
                    break;
                case 3:
                    deleteGoal(scanner, goalService);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }


    private static void addGoal(Scanner scanner, GoalService goalService, User currentUser) {
        System.out.print("Введите название цели: ");
        String goalName = scanner.nextLine();

        System.out.print("Введите необходимую сумму: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        goalService.createGoal(currentUser.getId(), goalName, amount);
        System.out.println("Цель добавлена успешно!");
    }

    private static void viewGoals(GoalService goalService, User currentUser) {
        List<Goal> goals = goalService.viewGoals(currentUser.getId());
        if (goals.isEmpty()) {
            System.out.println("У вас нет целей.");
        } else {
            System.out.println("Ваши цели:");
            for (Goal goal : goals) {
                System.out.println(goal);
            }
        }
    }

    private static void deleteGoal(Scanner scanner, GoalService goalService) {
        System.out.print("Введите ID цели для удаления: ");
        long goalId = scanner.nextLong();
        scanner.nextLine();

        goalService.deleteGoal(goalId);
        System.out.println("Цель удалена.");
    }
}