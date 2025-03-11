package org.example.userInterface;

import org.example.model.User;
import org.example.service.UserService;

import java.util.Scanner;

public class Registration {
    public static User register(User currentUser, Scanner scanner, UserService userService) {
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        currentUser = userService.registerUser(email, password, name);
        return currentUser;
    }
}