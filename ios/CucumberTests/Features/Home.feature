Feature: Home
  Scenario: Login screen
    Given I am logged "in"
    Given My email is "email@email.com"
    Then I see the "Home" screen
    Then I see the "email@email.com" text
    Then I see the "Logout" button
    Then I press the "Logout" button
    Then I see the "Login" screen
