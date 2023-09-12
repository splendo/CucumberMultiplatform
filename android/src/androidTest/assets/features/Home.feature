Feature: Home
  Scenario: Home screen
    Given Email is "test@test.com"
    And I am in the "Home" screen
    Then I see "test@test.com" text
    And I see the "Logout" button
    And I press the "Logout" button
    And I see the "Login" screen
