package com.finance.manager.models.responses;

import com.finance.manager.models.User;

public record UserResponse(Long id, String username, PersonResponse person) {

    public static UserResponse fromEntity(User user) {
//        List<String> roles = user.getUserRoles() != null
//                ? user.getUserRoles().stream()
//                .map(userRole -> userRole.getRole().getName())
//                .toList()
//                : null;

        PersonResponse personResponse = user.getPerson() != null
                ? PersonResponse.fromEntity(user.getPerson())
                : null;

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                personResponse
        );
    }

}


