Feature: Login
  Scenario: Login screen
    Given I am in the "Login" screen
    Then I see the "Email" textfield with text "Email"
    And I see the "Password" textfield with text "Password"
    And I see the "Login" button
    When I type "test@test.com" in the "Email" textfield
    And I type "1234" in the "Password" secure textfield
    And I press the "Login" button
    Then I see the "Home" screen
    And I see "test@test.com" text
