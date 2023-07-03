Feature: Login

  Scenario: Failed attempt with wrong credentials
    Given I am in the "Login" screen
    Then I see the "Email" textfield with text "Email"
    Then I type "test@test.comunism" in the email field
    Then I see the "Password" textfield with text "Password"
    Then I type "1234" in the password field
    Then I see the "Login" button
    Then I press the login button
    Then I see the "Login" screen
    Then I see the "Incorrect email or password" screen

  Scenario: Failed attempt with empty email
    Given I am in the "Login" screen
    Then I see the "Email" textfield with text "Email"
    Then I see the "Password" textfield with text "Password"
    Then I type "1234" in the password field
    Then I see the "Login" button
    Then I press the login button
    Then I see the "Login" screen
    Then I see the "Missing email" screen

  Scenario: Failed attempt with empty password
    Given I am in the "Login" screen
    Then I see the "Email" textfield with text "Email"
    Then I see the "Password" textfield with text "Password"
    Then I type "1234" in the password field
    Then I see the "Login" button
    Then I press the login button
    Then I see the "Login" screen
    Then I see the "Missing password" screen

  Scenario: Successful attempt
    Given I am in the "Login" screen
    Then I see the "Email" textfield with text "Email"
    Then I type "test@test.com" in the email field
    Then I see the "Password" textfield with text "Password"
    Then I type "1234" in the password field
    Then I see the "Login" button
    Then I press the login button
    Then I see the "Home" screen
    Then I see "test@test.com" text