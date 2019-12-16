@get-poem
Feature: Executing a GET request

  Scenario: Getting /poem returns a 200
    Given I make a GET request to "/poem"
    Then my response should have status code 200
    And my response should have an empty body
