Feature: Executing a POST request

  @post-letter
  Scenario: Posting returns the letter
    Given I make a POST with parameters to "/letter"
    Then my response should have status code 200
    And my response body should be the formatted letter