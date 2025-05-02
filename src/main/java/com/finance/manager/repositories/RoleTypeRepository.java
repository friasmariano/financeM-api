package com.finance.manager.repositories;

import com.finance.manager.models.RoleType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleTypeRepository extends CrudRepository<RoleType, Long> {
    Optional<RoleType> findByType(String type);

}
