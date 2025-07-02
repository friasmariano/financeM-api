package com.finance.manager.repositories;

import com.finance.manager.models.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {
    Optional<Person> findByEmail(String username);
    boolean existsByEmail(String email);
}
