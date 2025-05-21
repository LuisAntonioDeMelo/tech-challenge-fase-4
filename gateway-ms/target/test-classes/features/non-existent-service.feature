Feature: Non-existent Service Route
  As an API consumer
  I want to receive appropriate errors when accessing non-existent services
  So that I can handle errors gracefully

  Scenario: Request to non-existent service
    When I send a GET request to "/api/non-existent-service/resource"
    Then I should receive status code 404
    And the response content type should be "application/json"
    And the response body should contain:
      """
      {"status":404,"message":"Service not found"}
      """
