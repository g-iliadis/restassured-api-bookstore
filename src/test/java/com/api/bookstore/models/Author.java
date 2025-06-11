package com.api.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record Author(
        Integer id,
        Integer idBook,
        String firstName,
        String lastName
) {

    public boolean isValid() {
        return firstName != null && !firstName.isEmpty()
                && lastName != null && !lastName.isEmpty()
                && idBook != null && idBook > 0;
    }

    public String fullName() {
        return firstName + " " + lastName;
    }
}