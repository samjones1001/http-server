@simple-get
Feature: Executing a simple GET request

  Scenario: Getting /simple_get returns a 200
    Given I make a GET request to "/simple_get"
    Then my response should have status code 200
    And my response should have an empty body
