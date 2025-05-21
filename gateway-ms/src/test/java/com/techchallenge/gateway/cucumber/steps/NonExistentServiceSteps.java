package com.techchallenge.gateway.cucumber.steps;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

public class NonExistentServiceSteps {


    private WebTestClient webTestClient;

    private WebTestClient.ResponseSpec response;

    @When("I send a GET request to {string}")
    public void iSendAGETRequestTo(String path) {
        response = webTestClient
                .get()
                .uri(path)
                .exchange();
    }

    @Then("I should receive status code {int}")
    public void iShouldReceiveStatusCode(int statusCode) {
        response.expectStatus().isEqualTo(statusCode);
    }

    @And("the response content type should be {string}")
    public void theResponseContentTypeShouldBe(String contentType) {
        response.expectHeader().contentType(contentType);
    }

    @And("the response body should contain:")
    public void theResponseBodyShouldContain(String expectedBody) {
        response.expectBody()
                .json(expectedBody);
    }
}
