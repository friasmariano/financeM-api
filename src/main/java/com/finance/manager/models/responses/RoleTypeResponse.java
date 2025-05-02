package com.finance.manager.models.responses;

public class RoleTypeResponse {
    public Long id;
    public String type;

    public RoleTypeResponse(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
