package com.finance.manager.config;

import com.finance.manager.models.*;
import com.finance.manager.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner commandLineRunner(RoleTypeRepository roleTypeRepository,
                                               RoleRepository roleRepository,
                                               BudgetCategoryRepository budgetCategoryRepository,
                                               PersonRepository personRepository,
                                               UserRepository userRepository,
                                               UserRoleRepository userRoleRepository,
                                               PasswordEncoder passwordEncoder) {
        return args -> {
            if(roleTypeRepository.count() == 0) {
                roleTypeRepository.saveAll(List.of(
                        new RoleType("USER"),
                        new RoleType("ADMIN"),
                        new RoleType("SYS")
                ));
            }

            if(roleRepository.count() == 0) {
                RoleType userType = roleTypeRepository.findByType("USER").orElseThrow();
                RoleType adminType = roleTypeRepository.findByType("ADMIN").orElseThrow();

                roleRepository.saveAll(List.of(
                        new Role("ROLE_USER", userType),
                        new Role("ROLE_ADMIN", adminType)
                ));
            }

            if(budgetCategoryRepository.count() == 0) {
                budgetCategoryRepository.saveAll(List.of(
                        new BudgetCategory("Fixed"),
                        new BudgetCategory("Flexible"),
                        new BudgetCategory("Zero-Based"),
                        new BudgetCategory("50/30/20")
                ));
            }

            if(personRepository.count() == 0) {
                personRepository.saveAll(List.of(
                        new Person("System", "Administrator", "admin@financem.com", 1),
                        new Person("Default", "User", "user@financem.com", 1)
                ));
            }

            if(userRepository.count() == 0) {
                Person adminPerson = personRepository.findById(1L).orElseThrow();
                Person userPerson = personRepository.findById(2L).orElseThrow();

                userRepository.saveAll(List.of(
                        new User("admin", passwordEncoder.encode("admin"), adminPerson, 1),
                        new User("user", passwordEncoder.encode("user"), userPerson, 1)
                ));
            }

            if(userRoleRepository.count() == 0) {
                User adminUser = userRepository.findByUsername("admin").orElseThrow();
                User normalUser = userRepository.findByUsername("user").orElseThrow();

                Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
                Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow();

                userRoleRepository.saveAll(List.of(
                        new UserRole(adminUser, adminRole),
                        new UserRole(normalUser, userRole)
                ));
            }
        };
    }
}
