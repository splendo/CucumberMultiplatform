Feature: Home
  Scenario: Home screen
    Given Email is "test@test.com"
    And I am in the "Home" screen
    Then I see "test@test.com" text
    And I see the "Logout" button
    When I press the "Logout" button
    Then I see the "Login" screen
