package com.api.bookstore.api.base;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * Generic CRUD wrapper for Rest-Assured tests.
 *
 * @param <T> logical resource DTO (optional; keep if you want type-stamped subclasses)
 */
public abstract class GenericBaseAPI<T> {

    protected final String baseUrl;
    protected final String resourcePath;

    protected GenericBaseAPI(String baseUrl, String resourcePath) {
        this.baseUrl = baseUrl.replaceAll("/$", "");
        this.resourcePath = resourcePath.replaceAll("^/", "");
    }

    protected RequestSpecification spec() {
        return given()
                .baseUri(baseUrl)
                .basePath(resourcePath)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all()
                .log().ifValidationFails();
    }

    @Step("Get all {resourcePath} from {baseUrl}")
    public Response getAll() {
        return spec().when().get();
    }

    @Step("Get {resourcePath} by ID: {id} from {baseUrl}")
    public Response getById(int id) {
        return spec().when().get("/{id}", id);
    }

    @Step("Get {resourcePath} by ID and deserialize")
    public <R> R getById(int id, Class<R> responseType) {
        return getById(id)
                .then()
                .statusCode(200)
                .extract()
                .as(responseType);
    }

    @Step("Get all {resourcePath} and deserialize")
    public <R> R getAll(Class<R> responseType) {
        return getAll()
                .then()
                .statusCode(200)
                .extract()
                .as(responseType);
    }

    /**
     * Low-level: send POST and return raw Response.
     */
    @Step("Create new resource at {baseUrl}/{resourcePath}")
    public <B> Response create(B body) {
        return spec()
                .body(body)
                .when()
                .post();
    }

    @Step("Create resource and return as type")
    public <B, R> R create(B body, Class<R> responseType) {
        return create(body)          // ← calls the 1-arg overload above
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .as(responseType);
    }

    @Step("Update resource with ID: {id}")
    public <B> Response update(int id, B body) {
        return spec()
                .body(body)
                .when()
                .put("/{id}", id);
    }

    @Step("Partially update resource with ID: {id}")
    public <B> Response patch(int id, B body) {
        return spec()
                .body(body)
                .when()
                .patch("/{id}", id);
    }

    @Step("Delete resource with ID: {id} from {baseUrl}")
    public Response delete(int id) {
        return spec().when().delete("/{id}", id);
    }

    public String getFullUrl() {
        return baseUrl + "/" + resourcePath;
    }
}
