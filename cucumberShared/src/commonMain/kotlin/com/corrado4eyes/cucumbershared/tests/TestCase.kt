package com.corrado4eyes.cucumbershared.tests

import com.corrado4eyes.cucumber.CucumberDefinition
import com.corrado4eyes.cucumber.Definition
import com.corrado4eyes.cucumber.EXPECT_VALUE_STRING
import com.corrado4eyes.cucumber.GherkinTestCase

enum class Definitions(override val definition: Definition): GherkinTestCase<Definition> {
    I_AM_IN_THE_EXPECT_VALUE_STRING_SCREEN(
        CucumberDefinition.Step.Given("I am in the $EXPECT_VALUE_STRING screen")
    ),
    I_SEE_EXPECT_VALUE_STRING_TEXT(
        CucumberDefinition.Step.Then("I see $EXPECT_VALUE_STRING text")
    ),
    I_SEE_THE_EXPECT_VALUE_STRING_BUTTON(
        CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING button")
    ),
    I_SEE_THE_EXPECT_VALUE_STRING_SCREEN(
        CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING screen")
    ),
    I_SEE_THE_EXPECT_VALUE_STRING_TEXT_FIELD_WITH_TEXT_EXPECT_VALUE_STRING(
        CucumberDefinition.Step.Then("I see the $EXPECT_VALUE_STRING textfield with text $EXPECT_VALUE_STRING")
    ),
    I_TYPE_EXPECT_VALUE_STRING_IN_THE_EXPECT_VALUE_STRING_TEXT_FIELD(
        CucumberDefinition.Step.When("I type $EXPECT_VALUE_STRING in the $EXPECT_VALUE_STRING textfield")
    ),
    I_TYPE_EXPECT_VALUE_STRING_IN_THE_EXPECT_VALUE_STRING_SECURE_TEXT_FIELD(
        CucumberDefinition.Step.When("I type $EXPECT_VALUE_STRING in the $EXPECT_VALUE_STRING secure textfield")
    ),
    I_PRESS_THE_EXPECT_VALUE_STRING_BUTTON(
        CucumberDefinition.Step.When("I press the $EXPECT_VALUE_STRING button")
    ),
    EMAIL_IS_EXPECT_VALUE_STRING(
        CucumberDefinition.Step.Given("Email is $EXPECT_VALUE_STRING")
    );

    companion object {
        val allCases: List<Definitions> = Definitions.values().toList()
    }
}
