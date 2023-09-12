package com.corrado4eyes.cucumbershared.tests

import com.corrado4eyes.cucumber.CucumberDefinition
import com.corrado4eyes.cucumber.Definition
import com.corrado4eyes.cucumber.EXPECT_VALUE_STRING
import com.corrado4eyes.cucumber.GherkinTestCase

enum class TestCases(override val definition: Definition): GherkinTestCase<Definition> {
    SCREEN_IS_VISIBLE(
        CucumberDefinition.Step.Given("I am in the $EXPECT_VALUE_STRING screen")
    ),
    TEXT_IS_VISIBLE(
        CucumberDefinition.Step.Then("I see $EXPECT_VALUE_STRING text")
    ),
    BUTTON_IS_VISIBLE(
        CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING button")
    ),
    NAVIGATE_TO_SCREEN(
        CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING screen")
    ),
    TEXTFIELD_IS_VISIBLE(
        CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING textfield with text $EXPECT_VALUE_STRING")
    ),
    FILL_TEXTFIELD(
        CucumberDefinition.Step.Then("I type $EXPECT_VALUE_STRING in the $EXPECT_VALUE_STRING textfield")
    ),
    FILL_SECURE_TEXTFIELD(
        CucumberDefinition.Step.Then("I type $EXPECT_VALUE_STRING in the $EXPECT_VALUE_STRING secure textfield")
    ),
    PRESS_BUTTON(
        CucumberDefinition.Step.Then("I press the $EXPECT_VALUE_STRING button")
    ),
    USER_IS_LOGGED_IN(
        CucumberDefinition.Step.Given("Email is $EXPECT_VALUE_STRING")
    );

    companion object {
        val allCases: List<TestCases> = TestCases.values().toList()
    }
}
