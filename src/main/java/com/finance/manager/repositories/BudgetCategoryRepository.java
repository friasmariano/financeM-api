package com.finance.manager.repositories;

import com.finance.manager.models.BudgetCategory;
import org.springframework.data.repository.CrudRepository;

public interface BudgetCategoryRepository extends CrudRepository<BudgetCategory, Long> {
}
