package org.example.repository;

import org.example.model.Transaction;

import java.util.*;

public class TransactionRepository {
    private Map<Long, Transaction> transactionMap = new HashMap<>();
    private static long idCount = 1;

    public boolean save(Transaction transaction) {
        transaction.setId(idCount++);
        transactionMap.put(transaction.getId(), transaction);
        return true;
    }

    public Optional<Transaction> findById(long id) {
        return Optional.ofNullable(transactionMap.get(id));
    }

    public boolean update(long id, Transaction transaction) {
        if (!transactionMap.containsKey(id)) {
            return false;
        }
        transactionMap.put(id, transaction);
        return true;
    }

    public boolean delete(long id) {
        return transactionMap.remove(id) != null;
    }

    public List<Transaction> findByUserId(long userId) {
        List<Transaction> userTransactions = new ArrayList<>();
        for (Transaction transaction : transactionMap.values()) {
            if (transaction.getUserId() == userId) {
                userTransactions.add(transaction);
            }
        }
        return userTransactions;
    }

    public List<Transaction> getAll() {
        return new ArrayList<>(transactionMap.values());
    }
}