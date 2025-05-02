package com.finance.manager.services;

import com.finance.manager.models.Role;
import com.finance.manager.models.RoleType;
import com.finance.manager.models.responses.RoleResponse;
import com.finance.manager.models.responses.RoleTypeResponse;
import com.finance.manager.repositories.RoleRepository;
import com.finance.manager.repositories.RoleTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleTypeRepository roleTypeRepository;

    public RoleService(RoleRepository roleRepository,
                       RoleTypeRepository roleTypeRepository) {
        this.roleRepository = roleRepository;
        this.roleTypeRepository = roleTypeRepository;
    }

    public List<RoleResponse> getAllRoles() {
        List<Role> roles = (List<Role>) roleRepository.findAll();

        return roles.stream()
                .map(role -> new RoleResponse(role.getId(), role.getName()))
                .collect(Collectors.toList());
    }

    public List<RoleTypeResponse> getAllRoleTypes() {
        List<RoleType> types = (List<RoleType>) roleTypeRepository.findAll();

        return types.stream()
                .map(type -> new RoleTypeResponse(type.getId(), type.getType()))
                .collect(Collectors.toList());
    }

}
