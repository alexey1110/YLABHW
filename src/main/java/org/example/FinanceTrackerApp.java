package org.example;

import org.example.model.User;
import org.example.service.UserService;
import org.example.service.GoalService;
import org.example.service.BudgetService;
import org.example.service.TransactionService;
import org.example.repository.UserRepository;
import org.example.repository.GoalRepository;
import org.example.repository.BudgetRepository;
import org.example.repository.TransactionRepository;
import org.example.userInterface.MainMenu;
import org.example.userInterface.UserMenu;

import java.util.Scanner;

public class FinanceTrackerApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static UserRepository userRepository = new UserRepository();
    private static TransactionRepository transactionRepository = new TransactionRepository();
    private static GoalRepository goalRepository = new GoalRepository();
    private static BudgetRepository budgetRepository = new BudgetRepository();
    private static UserService userService = new UserService(userRepository);
    private static GoalService goalService = new GoalService(goalRepository);
    private static BudgetService budgetService = new BudgetService(budgetRepository);
    private static TransactionService transactionService = new TransactionService(transactionRepository, goalService, budgetService);

    private static User currentUser = null;

    public static void main(String[] args) {
        while (true) {
            if (currentUser == null) {
                showMainMenu();
            } else {
                showUserMenu();
            }
        }
    }

    private static void showMainMenu() {
        currentUser = MainMenu.show(currentUser, scanner, userService);
    }

    private static void showUserMenu() {
        UserMenu.show(scanner, currentUser, transactionService, goalService, budgetService);
    }
}