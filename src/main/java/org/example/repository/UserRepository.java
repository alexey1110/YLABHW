package org.example.repository;

import org.example.model.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private Map<Long, User> userMap = new HashMap<>();
    private static long idCount = 1;

    public boolean save(User user) {
        if (user == null || isPasswordTaken(user.getPassword())) {
            return false;
        }
        if (!existEmail(user.getEmail())) {
            user.setId(idCount++);
            userMap.put(user.getId(), user);
            return true;
        }
        return false;
    }

    private boolean isPasswordTaken(String password) {
        return userMap.values().stream()
                .anyMatch(user -> user.getPassword().equals(password));
    }

    public boolean existEmail(String email) {
        return userMap.values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    public Optional<User> findByEmail(String email) {
        return userMap.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public boolean delete(String email) {
        Optional<User> user = findByEmail(email);
        return user.map(u -> {
            userMap.remove(u.getId());
            return true;
        }).orElse(false);
    }

    public boolean edit(User user, User newUser) {
        if (user == null || newUser == null) {
            return false;
        }
        if (!user.getEmail().equals(newUser.getEmail()) && existEmail(newUser.getEmail())) {
            return false;
        }
        if (isPasswordTaken(newUser.getPassword()) && !user.getPassword().equals(newUser.getPassword())) {
            return false;
        }
        newUser.setId(user.getId());
        userMap.put(newUser.getId(), newUser);
        return true;
    }

    public Map<Long, User> getAllUsers() {
        return new HashMap<>(userMap);
    }
}