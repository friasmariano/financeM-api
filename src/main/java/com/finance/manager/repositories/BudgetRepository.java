package com.finance.manager.repositories;

import com.finance.manager.models.Budget;
import com.finance.manager.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends CrudRepository<Budget, Long> {
    List<Budget> findByUser(User user);
    boolean existsByNameAndUser(String name, User user);
    Optional<Budget> findByNameAndUser(String name, User user);
}
