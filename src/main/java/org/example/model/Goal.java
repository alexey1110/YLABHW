package org.example.model;

public class Goal {
    private long id;
    private long userId;
    private String name;
    private double targetAmount;
    private double currentAmount;

    public Goal(long id, long userId, String name, double targetAmount) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = 0;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void addSavings(double amount) {
        this.currentAmount += amount;
    }

    public boolean isCompleted() {
        return currentAmount >= targetAmount;
    }

    @Override
    public String toString() {
        return "Id: " + id + " Цель: " + name + " (накоплено " + currentAmount + " из " + targetAmount + ")";
    }
}