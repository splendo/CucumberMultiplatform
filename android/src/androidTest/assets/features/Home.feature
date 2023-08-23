Feature: Home

  Scenario: Logout
    Given I see the "Home screen" screen
    Then I see the "test@test.com" text
    Then I see the "Logout" button
    Then I press the "Logout" button
    Then I see the "Login screen" text