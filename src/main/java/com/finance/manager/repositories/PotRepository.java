package com.finance.manager.repositories;

import com.finance.manager.models.Pot;
import com.finance.manager.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PotRepository extends CrudRepository<Pot, Long> {
    boolean existsByUserAndName(User user, String name);
    List<Pot> findAllByUser(User user);
    Optional<Pot> findByIdAndUser(Long id, User user);
}
