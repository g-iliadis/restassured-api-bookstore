package com.api.bookstore.api;

import com.api.bookstore.api.base.BooksBaseAPI;
import com.api.bookstore.factory.TestDataFactory;
import com.api.bookstore.models.Book;
import com.api.bookstore.utils.Config;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Listeners({AllureTestNg.class})
public class BooksAPIIT {

    private BooksBaseAPI booksApi;
    private TestDataFactory dataFactory;

    @BeforeClass
    public void setup() {
        Config.configure();
        booksApi = new BooksBaseAPI();
        dataFactory = new TestDataFactory();
    }

    @Test
    @Issue("BST-23")
    @Description("Verify getting all books returns 200")
    public void getAllBooks_Returns200() {
        booksApi.getAll().then().statusCode(200).body("$", not(empty()));
    }

    @Test
    @Issue("BST-24")
    @Description("Create a new book with POJO")
    public void createBook_WithValidData_Returns200() {
        Book newBook = dataFactory.books().create();
        Book createdBook = booksApi.createBook(newBook);

        assertNotNull(createdBook.id());
        assertEquals(createdBook.title(), newBook.title());
        assertEquals(createdBook.description(), newBook.description());
    }

    @Test
    @Issue("BST-25")
    @Description("Verify duplicate book ID returns 400")
    public void createBook_DuplicateId_Returns400() {
        Book firstBook = dataFactory.books().create();
        Book createdBook = booksApi.createBook(firstBook);
        Book duplicateBook = dataFactory.books().createWithId(createdBook.id());

        booksApi.create(duplicateBook).then().statusCode(400).body("error", containsString("already exists"));
    }

    @Test
    @Issue("BST-26")
    @Description("Get book by ID returns correct book")
    public void getBookById_ExistingBook_ReturnsBook() {
        Book newBook = dataFactory.books().create();
        Book createdBook = booksApi.createBook(newBook);
        Book retrievedBook = booksApi.getBook(createdBook.id());

        assertEquals(retrievedBook.id(), createdBook.id());
        assertEquals(retrievedBook.title(), createdBook.title());
    }

    @Test
    @Issue("BST-27")
    @Description("Get book by non-existent ID returns 404")
    public void getBookById_NonExistent_Returns404() {
        int nonExistentId = 999999999;

        booksApi.getById(nonExistentId).then().statusCode(404);
    }

    @Test
    @Issue("BST-28")
    @Description("Delete existing book returns 200")
    public void deleteBook_ExistingBook_Returns200() {
        Book book = booksApi.createBook(dataFactory.books().create());
        booksApi.delete(book.id())
                .then().statusCode(200);

        booksApi.getById(book.id())
                .then().statusCode(404);
    }

    @Test
    @Issue("BST-29")
    @Description("Delete non-existent book returns 404")
    public void deleteBook_NonExistent_Returns404() {
        int nonExistentId = 999999999;
        booksApi.delete(nonExistentId).then().statusCode(404);
    }

    @Test
    @Issue("BST-30")
    @Description("Update a book and verify the changed fields")
    public void updateBook_WithValidData_ReturnsUpdatedBook() {
        Book originalBook = booksApi.createBook(
                dataFactory.books().create()
        );

        Book updatedPayload = originalBook.toBuilder()
                .title("Updated Title")
                .description("Updated Description")
                .build();

        Response updateResponse =
                booksApi.update(originalBook.id(), updatedPayload);

        updateResponse.then()
                .statusCode(200)
                .body("title", equalTo("Updated Title"))
                .body("description", equalTo("Updated Description"));
    }
}