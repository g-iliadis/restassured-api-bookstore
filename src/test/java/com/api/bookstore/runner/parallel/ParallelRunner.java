package com.api.bookstore.runner.parallel;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(plugin = {"pretty",
        "json:target/cucumber-report/cucumber.json",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        features = {"src/test/resources/features/parallel"},
        tags = "(@API) and (not @Ignored)", glue = {"com/api/bookstore/stepdef"},
        monochrome = true)

public class ParallelRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
