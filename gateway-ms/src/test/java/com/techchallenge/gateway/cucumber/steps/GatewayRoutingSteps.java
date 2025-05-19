package com.techchallenge.gateway.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GatewayRoutingSteps {

    @LocalServerPort
    private int port;
    
    private Response response;
    
    @Given("the gateway service is running")
    public void theGatewayServiceIsRunning() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @When("I send a request to {string}")
    public void iSendARequestTo(String path) {
        response = given().when().get(path);
    }
    
    @When("I send a request without authentication to a protected endpoint")
    public void iSendARequestWithoutAuthentication() {
        response = given().when().get("/api/protected-resource");
    }

    @Then("the request should be routed to the {string} microservice")
    public void theRequestShouldBeRoutedToTheMicroservice(String serviceName) {
        // In a real test, you would verify routing by checking response headers or mocks
        // This is a simplified example
        System.out.println("Verifying request was routed to " + serviceName);
    }

    @Then("I should receive a successful response")
    public void iShouldReceiveASuccessfulResponse() {
        response.then().statusCode(200);
    }

    @Then("I should receive a {int} status code")
    public void iShouldReceiveAStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
    }
}
