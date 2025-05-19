package com.techchallenge.gateway.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.techchallenge.gateway.cucumber.steps",
    plugin = {"pretty", "html:target/cucumber-reports"}
)
public class CucumberTestRunner {
    // This class serves as an entry point for Cucumber tests
}
