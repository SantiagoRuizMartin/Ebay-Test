Feature: Basic ebay tests

  Background:
    Given I enter to ebay shop
    And I select the product shoes
    And I select the PUMA brand and size 10

  @F1S1
  Scenario: Navigate to ebay site and validate that the number of results for a product and given size is printed
    Then The number of results should be printed


  @F1S2
  Scenario: Navigate to ebay site and order the results by price ascendant
    When I click to sort the result by price ascending
    Then The results should be sorted in ascending


  @F1S3
  Scenario: Navigate to ebay site  and order the results by price ascendant and Assert the order taking the first 5 results
    When I click to sort the result by price ascending
    Then I assert the order taking the first 5 results


  @F1S4
  Scenario: Navigate to ebay site and Take the first 5 products with their prices and print them in console.
    When I click to sort the result by price ascending
    Then The first 5 products with their prices should be printed in console


  @F1S5
  Scenario: Navigate to ebay site and order and print the products by name (ascendant)
    When I Order the products result by name ascending
    Then The products sorted should be printed


  @F1S6
  Scenario: Navigate to ebay site and order and print the products by price descendant mode
    When I Order the products result by price descendant
    Then The products should be printed