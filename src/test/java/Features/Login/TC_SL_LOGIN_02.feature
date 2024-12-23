Feature: TC_SL_LOGIN_02
  @TC_SL_LOGIN_02
  Scenario: Verify that login to Swag Labs with locked out user
    Given navigate to Swag Lab url
    When user is on login page
    And enter locked out user name and password
    And click login button
    Then user able to see this user has been locked out error