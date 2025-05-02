package com.finance.manager.models.responses;

public class RoleResponse {
    public Long id;
    public String name;

    public RoleResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
