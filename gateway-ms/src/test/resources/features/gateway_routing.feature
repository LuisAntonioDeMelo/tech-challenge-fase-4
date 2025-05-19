Feature: API Gateway Routing
  As an API client
  I want to access different microservices through the gateway
  So that I can interact with the system through a single entry point

  Scenario: Gateway routes requests to the appropriate microservice
    Given the gateway service is running
    When I send a request to "/api/some-service/resource"
    Then the request should be routed to the "some-service" microservice
    And I should receive a successful response

  Scenario: Gateway handles non-existent route
    Given the gateway service is running
    When I send a request to "/api/non-existent-service/resource"
    Then I should receive a 404 status code
    
  Scenario: Gateway performs authentication
    Given the gateway service is running
    When I send a request without authentication to a protected endpoint
    Then I should receive a 401 status code
