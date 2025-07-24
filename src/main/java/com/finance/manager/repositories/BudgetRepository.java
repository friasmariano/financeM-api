package com.finance.manager.repositories;

import com.finance.manager.models.Budget;
import com.finance.manager.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BudgetRepository extends CrudRepository<Budget, Long> {
    List<Budget> findByUser(User user);
}
