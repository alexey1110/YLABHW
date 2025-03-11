package org.example.service;

import org.example.model.Goal;
import org.example.repository.GoalRepository;

import java.util.List;
import java.util.Optional;

public class GoalService {
    private GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public void createGoal(long userId, String name, double targetAmount) {
        Goal goal = new Goal(0, userId, name, targetAmount);
        goalRepository.save(goal);
        System.out.println("Цель '" + name + "' создана.");
    }

    public void addSavingsToGoal(long goalId, double amount) {
        Optional<Goal> goalOpt = goalRepository.findById(goalId);
        if (goalOpt.isPresent()) {
            Goal goal = goalOpt.get();
            goal.addSavings(amount);
            System.out.println("Добавлено " + amount + " к цели '" + goal.getName() + "'.");
            if (goal.isCompleted()) {
                System.out.println("Поздравляем! Цель '" + goal.getName() + "' достигнута!");
            }
        } else {
            System.out.println("Цель не найдена.");
        }
    }

    public List<Goal> viewGoals(long userId) {
        List<Goal> goals = goalRepository.findByUserId(userId);
        if (goals.isEmpty()) {
            System.out.println("У вас нет активных целей.");
            return List.of();
        } else {
            return goals;
        }
    }

    public void deleteGoal(long goalId) {
        if (goalRepository.delete(goalId)) {
            System.out.println("Цель удалена.");
        } else {
            System.out.println("Цель не найдена.");
        }
    }
}