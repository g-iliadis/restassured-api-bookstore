package com.api.bookstore.stepdef;

import com.api.bookstore.factory.TestDataFactory;
import com.api.bookstore.utils.Config;
import com.api.bookstore.utils.ObjectManager;
import com.api.bookstore.utils.TestContext;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    private final TestContext ctx;

    public Hooks(TestContext ctx) {
        this.ctx = ctx;
    }

    @Before(order = 0)
    public void initApiManager(Scenario scenario) {
        logger.info("Starting scenario: {}", scenario.getName());
        Config.configure();
        ctx.setPageObjectManager(new ObjectManager());
        ctx.setDataFactory(new TestDataFactory());
    }

}
