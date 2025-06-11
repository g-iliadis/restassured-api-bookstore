package com.api.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record Book(
        Integer id,
        String title,
        String description,
        Integer pageCount,
        String excerpt,
        String publishDate
) {

    public boolean isValid() {
        return title != null && !title.isEmpty()
                && description != null && !description.isEmpty()
                && pageCount != null && pageCount > 0;
    }
}
