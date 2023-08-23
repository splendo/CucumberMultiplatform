Feature: Login

  Scenario: Failed attempt with wrong credentials
    Given I see the "Login screen" screen
    Then I see the "Email" textfield with text "Email"
    Then I type "test@test.comunism" in the "Email" field
    Then I see the "Password" textfield with text "Password"
    Then I type "1234" in the "Password" field
    Then I see the "Login" button
    Then I press the "Login" button
    Then I see the "Login" text
    Then I see the "Incorrect email or password" text

  Scenario: Failed attempt with empty email
    Given I see the "Login screen" screen
    Then I see the "Email" textfield with text "Email"
    Then I see the "Password" textfield with text "Password"
    Then I type "1234" in the "Password" field
    Then I see the "Login" button
    Then I press the "Login" button
    Then I see the "Login" text
    Then I see the "Missing email" text

  Scenario: Failed attempt with empty password
    Given I see the "Login screen" screen
    Then I see the "Email" textfield with text "Email"
    Then I see the "Password" textfield with text "Password"
    Then I type "alex@alex" in the "Email" field
    Then I see the "Login" button
    Then I press the "Login" button
    Then I see the "Login" text
    Then I see the "Missing password" text

  Scenario: Successful attempt
    Given I see the "Login screen" screen
    Then I see the "Email" textfield with text "Email"
    Then I type "test@test.com" in the "Email" field
    Then I see the "Password" textfield with text "Password"
    Then I type "1234" in the "Password" field
    Then I see the "Login" button
    Then I press the "Login" button
    Then I see the "Home screen" text
    Then I see the "test@test.com" text