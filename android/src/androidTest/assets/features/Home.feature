Feature: Home

  Scenario: Logout
    Given Email is "test@test.com"
    Given I am in the "Home" screen
    Then I see "test@test.com" text
    Then I see the "Logout" button
    Then I press the logout button
    Then I see the "Login" screen
