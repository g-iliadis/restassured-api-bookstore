package com.api.bookstore.api.base;

import com.api.bookstore.constants.BookstoreUris;
import com.api.bookstore.models.Book;
import com.api.bookstore.utils.EnvironmentConfig;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

public class BooksBaseAPI extends GenericBaseAPI<Book> {

    public BooksBaseAPI() {
        super(
                EnvironmentConfig.get("base.bookstore.api.url"),
                BookstoreUris.BOOKS
        );
    }

    @Step("Get book by ID: {id}")
    public Book getBook(int id) {
        return getById(id, Book.class);
    }

    @Step("Get all books")
    public List<Book> getAllBooks() {
        return getAll()
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", Book.class);
    }

    @Step("Create book and return created book")
    public Book createBook(Book book) {
        return create(book, Book.class);
    }

    @Step("Update book with ID: {id}")
    public Book updateBook(int id, Book updated) {
        return update(id, updated)
                .then()
                .statusCode(200)
                .extract()
                .as(Book.class);
    }

    /**
     * Convenience overload that pulls the id from the record.
     */
    @Step("Update book with ID: {updated.id()}")
    public Book updateBook(Book updated) {
        return updateBook(updated.id(), updated);
    }

    @Step("Delete book by ID: {id}")
    public Response deleteBook(int id) {
        return delete(id).then().statusCode(200).extract().response();
    }
}
