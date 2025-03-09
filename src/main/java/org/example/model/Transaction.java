package org.example.model;

import org.example.model.transactionEnum.Category;
import org.example.model.transactionEnum.TransactionType;

import java.time.LocalDate;

public class Transaction {
    private long id;
    private long userId;
    private double amount;
    private LocalDate date;
    private Category category;
    private String description;
    private TransactionType type;
    private Long goalId;

    public Transaction( long userId, double amount, LocalDate date, Category category, String description, TransactionType type, Long goalId) {
        this.userId = userId;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.description = description;
        this.type = type;
        this.goalId = goalId;
    }

    public Long getGoalId() {
        return goalId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
