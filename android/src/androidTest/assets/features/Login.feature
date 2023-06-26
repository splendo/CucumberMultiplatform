Feature: Login
  Scenario: Login screen
    Given I am logged "out"
    Then I see the "Login" screen
    Then I see the "Email" field
    Then I type a random email in the "Email" field
    Then I see the "Password" field
    Then I type a random text in the "Password" field
    Then I see the "Login" button
    Then I press the "Login" button
    Then I see the "Details" screen
