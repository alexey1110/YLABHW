package org.example.userInterface;

import org.example.model.User;
import org.example.service.UserService;

import java.util.Scanner;

public class MainMenu {
    public static User show(User currentUser, Scanner scanner, UserService userService) {
        System.out.println("\n--- Личный финансовый трекер ---");
        System.out.println("1. Регистрация");
        System.out.println("2. Вход");
        System.out.println("3. Выход");
        System.out.print("Выберите опцию: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                currentUser = Registration.register(currentUser, scanner, userService);
                break;
            case 2:
                currentUser = Login.login(currentUser, scanner, userService);
                break;
            case 3:
                System.out.println("До свидания!");
                System.exit(0);
                break;
            default:
                System.out.println("Неверный выбор, попробуйте снова.");
        }
        return currentUser;
    }
}