package com.finance.manager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "role_Type_id")
    private RoleType roleType;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;

    public Role() {}

    public Role(Long id, String name, RoleType roleType, List<UserRole> userRoles) {
        this.id = id;
        this.name = name;
        this.roleType = roleType;
        this.userRoles = userRoles;
    }

    public Role(String name, RoleType roleType) {
        this.name = name;
        this.roleType = roleType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
