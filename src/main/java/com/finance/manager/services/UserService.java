package com.finance.manager.services;

import com.finance.manager.exceptions.UserNotFoundException;
import com.finance.manager.models.Person;
import com.finance.manager.models.User;
import com.finance.manager.repositories.UserRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByPerson(Person person) {
        return userRepository.findByPerson(person);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User getAuthenticatedUser(Jwt jwt) {
        String subject = jwt.getSubject();
        return userRepository.findByUsername(subject)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

}
