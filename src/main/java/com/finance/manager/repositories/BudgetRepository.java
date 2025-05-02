package com.finance.manager.repositories;

import com.finance.manager.models.Budget;
import org.springframework.data.repository.CrudRepository;

public interface BudgetRepository extends CrudRepository<Budget, Long> {
}
