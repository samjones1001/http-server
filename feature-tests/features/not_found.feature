Feature: Returning a not found response

  @not-found
  Scenario: Getting an unconfigured resource returns a 404
    Given I make a GET request to "/not_found_resource"
    Then my response should have status code 404
