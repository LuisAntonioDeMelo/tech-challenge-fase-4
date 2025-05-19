Feature: Gateway Health Check
  As a system administrator
  I want to monitor the health of the gateway
  So that I can ensure the system is functioning properly

  Scenario: Health check endpoint returns UP status
    Given the gateway service is running
    When I send a request to the actuator health endpoint
    Then I should receive a successful response
    And the response should indicate that the service is "UP"
