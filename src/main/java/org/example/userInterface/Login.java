package org.example.userInterface;

import org.example.model.User;
import org.example.service.UserService;

import java.util.Scanner;

public class Login {
    public static User login(User currentUser, Scanner scanner, UserService userService) {
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        currentUser = userService.loginUser(email, password);
        return currentUser;
    }
}
