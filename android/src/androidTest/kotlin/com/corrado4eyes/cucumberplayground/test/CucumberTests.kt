package com.splendo.cucumberplayground.test

import io.cucumber.android.runner.CucumberAndroidJUnitRunner
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["features"],
    glue = ["com.splendo.cucumberplayground.test"]
)
class CucumberTests : CucumberAndroidJUnitRunner()