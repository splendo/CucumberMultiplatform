Feature: Login
  Scenario: Login screen
    Given I am logged "out"
    Given I am in the "Login" screen
    Then I see the "Email" textfield with text "Email"
    Then I type an email in the email field
    Then I see the "Password" textfield with text "Password"
    Then I type a password in the password field
    Then I see the "Login" button
    Then I press the login button
    # Then I see the "Home" screen
