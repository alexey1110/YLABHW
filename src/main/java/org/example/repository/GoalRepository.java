package org.example.repository;

import org.example.model.Goal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GoalRepository {
    private final List<Goal> goals = new ArrayList<>();
    private long nextId = 1;

    public void save(Goal goal) {
        goal = new Goal(nextId++, goal.getUserId(), goal.getName(), goal.getTargetAmount());
        goals.add(goal);
    }

    public Optional<Goal> findById(long goalId) {
        return goals.stream()
                .filter(goal -> goal.getId() == goalId)
                .findFirst();
    }

    public List<Goal> findByUserId(long userId) {
        return goals.stream()
                .filter(goal -> goal.getUserId() == userId)
                .toList();
    }

    public boolean delete(long goalId) {
        for (Goal goal : goals){
            if (goal.getId() == goalId){
                goals.remove(goal);
                return true;
            }
        }
        return false;
    }
}