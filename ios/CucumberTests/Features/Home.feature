Feature: Home
  Scenario: Home screen
    Given Email is "test@test.com"
    Given I see the "Home screen" screen
    Then I see the "test@test.com" text
    Then I see the "Logout" button
    Then I press the "Logout" button
    Then I see the "Login" screen
