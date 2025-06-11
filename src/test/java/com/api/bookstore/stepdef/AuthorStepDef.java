package com.api.bookstore.stepdef;

import com.api.bookstore.utils.TestContext;
import io.cucumber.java.en.Given;

public class AuthorStepDef {

    private final TestContext context;

    public AuthorStepDef(TestContext context) {
        this.context = context;

    }

    @Given("GetAuthors API performs properly")
    public void getAuthor() {
        //to do
    }


}
