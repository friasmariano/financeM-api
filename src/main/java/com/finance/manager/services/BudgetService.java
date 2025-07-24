
package com.finance.manager.services;

import com.finance.manager.exceptions.BudgetNotFoundException;
import com.finance.manager.exceptions.InvalidUserException;
import com.finance.manager.models.Budget;
import com.finance.manager.models.User;
import com.finance.manager.repositories.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public Budget create(Budget budget) {
        return budgetRepository.save(budget);
    }

    public List<Budget> getAll() {
        return StreamSupport
                .stream(budgetRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Budget getById(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new BudgetNotFoundException("Budget Not Found"));
    }

    public List<Budget> getByUser(User user) {
        if (user == null) {
            throw new InvalidUserException("User cannot be null");
        }

        return budgetRepository.findByUser(user);
    }

    public Budget update(Long id, Budget updatedB) {
        Budget existingB = getById(id);

        existingB.setCategory(updatedB.getCategory());
        existingB.setLimitAmount(updatedB.getLimitAmount());
        existingB.setUser(updatedB.getUser());

        return budgetRepository.save(existingB);
    }

    public void delete(Long id) {
        if (!budgetRepository.existsById(id)) {
            throw new BudgetNotFoundException("Budget not found with id: " + id);
        }

        budgetRepository.deleteById(id);
    }
}
