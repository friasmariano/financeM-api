package com.finance.manager.services;

import com.finance.manager.exceptions.EmailAlreadyUsedException;
import com.finance.manager.models.Person;
import com.finance.manager.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person save(Person person) {
        if (personRepository.existsByEmail(person.getEmail())) {
            throw new EmailAlreadyUsedException("Email is already in use");
        }

        return personRepository.save(person);
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    public Optional<Person> findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return personRepository.existsByEmail(email);
    }

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

}
