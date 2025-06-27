
package com.finance.manager.models.responses;

import com.finance.manager.models.Person;

public record PersonResponse(Long id, String firstName, String lastName) {

    public static PersonResponse fromEntity(Person person) {
        return new PersonResponse(
                person.getId(),
                person.getName(),
                person.getLastname()
        );
    }

}
