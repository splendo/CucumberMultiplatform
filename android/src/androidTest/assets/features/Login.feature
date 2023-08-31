Feature: Login

  # will run before each scenario
  Background:
    Given I see the "Login screen" screen
    Then I see the "Email" textfield with text "Email"
    And I see the "Password" textfield with text "Password"
    And I see the "Login" button

  Scenario: Failed attempt with wrong credentials
    Then I type "test@test.fail" in the "Email" field
    And I type "1234" in the "Password" field
    And I press the "Login" button
    And I see the "Incorrect email or password" text

  Scenario: Failed attempt with empty email
    Then I type "1234" in the "Password" field
    And I press the "Login" button
    And I see the "Missing email" text

  Scenario: Failed attempt with empty password
    Then I type "alex@alex" in the "Email" field
    Then I press the "Login" button
    Then I see the "Missing password" text

  Scenario: Successful attempt
    Then I type "test@test.com" in the "Email" field
    Then I type "1234" in the "Password" field
    Then I press the "Login" button
    Then I see the "Home screen" text
    Then I see the "test@test.com" text