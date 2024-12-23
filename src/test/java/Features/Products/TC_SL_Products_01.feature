Feature: TC_SL_Products_01
  @TC_SL_Products_01
  Scenario Outline:As a user, add the product to the shopping cart and then proceed to checkout successfully
    Given navigate to Swag Lab url
    When user is on login page
    And enter valid user name and valid password
    And click login button
    Then user able to see products screen
    And click product price container and sort the price high to low
    And add <totalItems> items which has $"<price>" price to the shopping cart
    And click shopping cart
    And user is able to see your cart screen with the two added items
    And click checkout button
    Then user able to see checkout your information page and need to fill the required information with "<FirstName>","<LastName>","<ZipPostal>"
    And click continue button
    Then user able to see checkout overview screen with log the checkout summary

    Examples:
      | totalItems | price | FirstName | LastName | ZipPostal |
      | 2          | 15.99 | Su        | Nandar   | 603219    |





