package org.example.repository;

import org.example.model.Budget;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BudgetRepository {

    Map<Long, Budget> budgetMap = new HashMap<>();

    public void save(Budget budget){
        budgetMap.put(budget.getUserId(), budget);
    }

    public Optional<Budget> findByUserId(long userId){
        return Optional.ofNullable(budgetMap.get(userId));
    }

    public boolean delete(long userId){
        return budgetMap.remove(userId) != null;
    }

    public boolean update(Budget budget){
        if(budgetMap.containsKey(budget.getUserId())){
            budgetMap.put(budget.getUserId(), budget);
            return true;
        } else {
            return false;
        }
    }
}
