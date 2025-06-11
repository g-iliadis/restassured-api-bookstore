package com.api.bookstore.stepdef;

import com.api.bookstore.api.base.BooksBaseAPI;
import com.api.bookstore.factory.TestDataFactory;
import com.api.bookstore.models.Book;
import com.api.bookstore.utils.TestContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.Map;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class BookStepDef {

    private final BooksBaseAPI   booksApi;
    private final TestDataFactory dataFactory;
    private final TestContext     context;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public BookStepDef(TestContext context) {
        this.context = context;
        this.booksApi =  context.getPageObjectManager().get(BooksBaseAPI.class);
        this.dataFactory = context.getDataFactory();
    }

    @Given("a user creates a book")
    public void postBook(DataTable table) {
        Map<String, String> overrides = table.asMaps().getFirst();
        Book payload = dataFactory.books().create(overrides);

        Book created = booksApi.createBook(payload);

        assertNotNull(created.id(), "Server should generate an id");
        context.set("book", created);
        context.set("bookId", created.id());
    }

    @And("the book exists with the proper info on the DB")
    public void validateBookInfoBasedOnID() throws JsonProcessingException {
        int id = context.get("bookId", Integer.class);
        Book expected = context.get("book", Book.class);
        Book actual = booksApi.getBook(id);
        String expectedJson = OBJECT_MAPPER.writeValueAsString(expected);
        String actualJson = OBJECT_MAPPER.writeValueAsString(actual);
        assertJsonEquals(expectedJson, actualJson);
    }

    @And("the book is no longer on the DB")
    public void bookDoesNotExist() {
        int id = context.get("bookId", Integer.class);
        booksApi.getById(id)
                .then()
                .statusCode(404);
    }

    @Then("user changes the book details")
    public void putBook(DataTable table) {
        int id = context.get("bookId", Integer.class);
        Book original = context.get("book", Book.class);

        Map<String, String> overrides = table.asMaps().getFirst();
        Book updatedPayload = dataFactory.books().update(original, overrides);
        Book serverCopy = booksApi.updateBook(id, updatedPayload);
        assertEquals(updatedPayload, serverCopy);
        context.set("book", serverCopy);
    }

    @Then("user deletes the book")
    public void deleteBook() {
        Response response = booksApi.deleteBook(context.get("bookId", Integer.class));
        response.then().statusCode(200);
    }


}
