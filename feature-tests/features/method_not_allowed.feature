@not-allowed
Feature: Returning a method not allowed response

  Scenario: Finding GET for an endpoint with only HEAD
    Given I make an GET request to "/get_with_body"
    Then my response should have status code 405
    And my response should have allowed headers of HEAD, OPTIONS
    And my response should have an empty body
