package com.api.bookstore.api.base;

import com.api.bookstore.constants.BookstoreUris;
import com.api.bookstore.models.Author;
import com.api.bookstore.utils.EnvironmentConfig;

public class AuthorsBaseAPI extends GenericBaseAPI<Author> {

    public AuthorsBaseAPI() {
        super(EnvironmentConfig.get("base.bookstore.api.url"), BookstoreUris.AUTHORS);
    }
}
