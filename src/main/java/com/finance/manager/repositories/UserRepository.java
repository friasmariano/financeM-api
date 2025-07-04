
package com.finance.manager.repositories;

import com.finance.manager.models.Person;
import com.finance.manager.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByPerson(Person person);
}
