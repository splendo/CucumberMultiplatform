Feature: Login
  Scenario: Login screen
    Given I am in the "Login" screen
    Then I see the "Email" textfield with text "Email"
    Then I type "test@test.com" in the "Email" textfield
    Then I see the "Password" textfield with text "Password"
    Then I type "1234" in the "Password" secure textfield
    Then I see the "Login" button
    Then I press the "Login" button
    Then I see the "Home" screen
    Then I see "test@test.com" text
