@get-image
Feature: Executing a GET request for an image

  Scenario: Getting /image returns an image
    Given I make a GET request to "/image"
    Then my response should have status code 200
    And my response should be an image