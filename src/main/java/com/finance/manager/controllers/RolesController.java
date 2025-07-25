package com.finance.manager.controllers;

import com.finance.manager.models.responses.RoleResponse;
import com.finance.manager.models.responses.RoleTypeResponse;
import com.finance.manager.services.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Tag(name = "Roles", description = "Handles roles creation, retrieval, updating and deletion")
public class RolesController {

    private final RoleService roleService;

    public RolesController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/getAll")
    public List<RoleResponse> getRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/getTypes")
    public List<RoleTypeResponse> getRoleTypes() {
        return roleService.getAllRoleTypes();
    }
}
