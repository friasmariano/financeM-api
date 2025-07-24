
package com.finance.manager.services;

import com.finance.manager.exceptions.BudgetCategoryAlreadyExists;
import com.finance.manager.exceptions.BudgetCategoryNotFoundException;
import com.finance.manager.models.BudgetCategory;
import com.finance.manager.repositories.BudgetCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BudgetCategoryService {

    private final BudgetCategoryRepository repository;

    @Autowired
    public BudgetCategoryService(BudgetCategoryRepository repository) {
        this.repository = repository;
    }

    public BudgetCategory create(String name) {
        if (repository.existsByName(name)) {
            throw new BudgetCategoryAlreadyExists("A category with this name already exists.");
        }

        BudgetCategory newCategory = new BudgetCategory(name);

        return repository.save(newCategory);
    }

    public Iterable<BudgetCategory> getAll() {
        return repository.findAll();
    }

    public Optional<BudgetCategory> getById(Long id) {
        return repository.findById(id);
    }

    public BudgetCategory update(Long id, String name) {
        BudgetCategory category = repository.findById(id)
                .orElseThrow(() -> new BudgetCategoryNotFoundException("Category with ID " + id + " wasn't found."));

        if (category.getName().equals(name)) {
            return category;
        }

        if (repository.existsByName(name)) {
            throw new BudgetCategoryAlreadyExists("A budget category with the name '" + name + "' already exists.");
        }

        category.setName(name);
        return repository.save(category);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new BudgetCategoryNotFoundException("Category with ID " + id + " wasn't found.");
        }
        repository.deleteById(id);
    }

    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }
}
