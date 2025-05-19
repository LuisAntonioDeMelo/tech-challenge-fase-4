package com.techchallenge.gateway.cucumber.steps;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HealthCheckSteps {
    
    private Response response;
    
    @When("I send a request to the actuator health endpoint")
    public void iSendARequestToTheActuatorHealthEndpoint() {
        response = given().when().get("/actuator/health");
    }
    
    @Then("the response should indicate that the service is {string}")
    public void theResponseShouldIndicateThatTheServiceIs(String status) {
        response.then().body("status", equalTo(status));
    }
}
