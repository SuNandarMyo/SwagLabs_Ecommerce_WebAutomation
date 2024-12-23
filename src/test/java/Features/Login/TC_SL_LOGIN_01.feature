Feature: TC_SL_LOGIN_01
  @TC_SL_LOGIN_01
  Scenario: Verify login with valid credential
    Given navigate to Swag Lab url
    When user is on login page
    And enter valid user name and valid password
    And click login button
    Then user able to see products screen
