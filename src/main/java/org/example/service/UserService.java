package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;

import java.util.Optional;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String email, String password, String name) {
        User user = new User(email, password, name);
        if (userRepository.save(user)) {
            System.out.println("User registered.");
            return user;
        } else {
            System.out.println("User with this email already exists.");
            return null;
        }
    }

    public User loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                System.out.println("User logged.");
                return user.get();
            } else {
                System.out.println("Incorrect password.");
            }
        } else {
            System.out.println("User not found.");
        }
        return null;
    }

    public void deleteUser(String email) {
        if (userRepository.delete(email)) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    public void editUser(String email, String newEmail, String newPassword, String newName) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            User updatedUser = new User(newEmail, newPassword, newName);
            updatedUser.setId(user.get().getId());

            if (userRepository.edit(user.get(), updatedUser)) {
                System.out.println("User updated.");
            } else {
                System.out.println("Update failed.");
            }
        } else {
            System.out.println("User not found.");
        }
    }
}
