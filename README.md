# Cucumber Playground

A wrapper for Android and iOS Cucumber SDKs + example.

## Structure
The project is divided into 3 kotlin modules + 1 ios project.
- `shared` represents the business logic of the example app.
- `cucumber` it is the wrapper for the Cucumber SDK.
- `cucumberShared` it is a module that includes both shared and cucumber modules. This is where `TestCase`s will be declared since it can access both module references. Also it exists because Cucumber platform SDK uses some test dependencies that should not be used from a main target (e.g. on iOS `XCTest`) otherwise it will lead to Runtime exceptions.

On Android `cucumberShared` can be imported on the `androidTest` target using `androidTestImplementation(project(":cucumberShared"))`. 
While on iOS `cucumberShared` will export `cucumber` module and the `Cucumberish` SDK by using the files created by the cocoapods plugin on the `cucumber` module.
Both `shared` and `cucumberShared` will create a framework called `shared` so that there won't be mismatches with the types on iOS. It's up to the developer to manually change the `FRAMEWORK_SEARCH_PATH` for the iOS targets so that the main target will point to the `projectDir/shared...` while the test target that contains the Cucumber test runner will set the FRAMEWORK_SEARCH_PATH as `projectDir/cucumberShared/...`  

## How does it work?
The project is based on a setup where the common sourceset wraps the 3 most used Gherkin keyword: Given, When and Then.

Define a `TestCase`s on each platform. A `TestCase` extends a `GherkinTestCase` and represents a kotlin like representation of a Gherkin step
with its generic regex, the action to be done when that line is read and what step it is. A `GherkinTestCase` is an interface with a `step` type, a `lambda`.

Lets take into example this 2 steps
```gherkin
When User is logged in
Given I am a "premium" user
Then I see the textfield "Premium field" with "Premium" text

```
A `TestCase` will be

```kotlin

interface GherkinTestCase<T: GherkinLambda> {
    val step: CucumberDefinition
    val lambda: T
}

sealed class TestCase<T: GherkinLambda> : GherkinTestCase<T> {
    class WhenUserIsLoggedIn(override val lambda: GherkinLambda0) : TestCase<GherkinLambda0>() {
        override val step: CucumberDefinition = CucumberDefinition.Step.Given("User is logged in")
    }
    
    class GivenUserIsType(override val lambda: GherkinLambda1) : TestCase<GherkinLambda1>() {
        override val step: CucumberDefinition = CucumberDefinition.Step.Given("I am in the ${EXPECT_VALUE_STRING} screen")
    }
    
    class ThenISeeATextField(override val lambda: GherkinLambda2) : TestCase() {
        override val step: CucumberDefinition = CucumberDefinition.Step.Then("I see the ${EXPECT_VALUE_STRING} textfield with ${EXPECT_VALUE_STRING} text")
    }
}
```

While it's easy to read and write a step type and the regex it represents, when talking about iOS it would be kind of hard to write everything in kotlin/native.
That's why in this example I preferred keeping the lambda definition on the platform side.

After setting up the classes on iOS and android, a developer will have to call the `TestCase`s declared in the common sourceset.
For example on iOS the implementation of the previously shown `TestCase`s will be

```swift
@objc public class CucumberishInitializer: NSObject {
    static var app: XCUIApplication!
    
    @objc public class func CucumberishSwiftInit() {
        
        before { _ in 
            app = XCUIApplication()
            app.launchArguments.append("test")
        }
        
        TestCase.GivenUserIsType { args, userInfo in
            val userType = args[0]
            if userType == "Premium" {
                // setup app mock to set a premium user
            } else {
                // setup app mocks to set a normal user
            }
        }
        TestCase.ThenISeeATextField { args, userInfo in
            val textFieldName = args[0]
            val text = args[1]
            let textFieldElement: XCUIElement = app.textFields[textFieldName]
            
            let predicate = NSPredicate(format: "exists == true")
            let expectation = XCTestCase().expectation(for: predicate, evaluatedWith: textFieldElement)
            XCTWaiter().wait(for: [expectation], timeout: timeout.rawValue)
            
            XCTAssert(textFieldElement?.value as? String == text, "\"\(textfieldName)\" field text should be \"\(textfield?.value)\"")
        }
        TestCase.WhenUserIsLoggedIn { args, userInfo in
            // Setup mocks to login the user
        }
        
        let bundle = Bundle(for: CucumberishInitializer.self)
        Cucumberish.executeFeatures(inDirectory: "${FEATURE_FILES_FOLDER}", from: bundle, includeTags: nil, excludeTags: ["ignore"])
    }
}
```

While the Android implementation will be split into 2+ files. A test runner file and one or more files to call the `TestCase`s

```kotlin
// Test runner class

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["features"],
    glue = ["com.corrado4eyes.cucumberplayground.test"]
)
class TestRunner : CucumberAndroidJUnitRunner()
```
- `features` will point to `androidTest/assets/features` folder
- `glue` represents the package where the step definitions are located

```kotlin
@WithJunitRule
class StepDefinitions : En {

    private val arguments = mutableMapOf<String, String>()
    private var scenario: ActivityScenario<*>? = null

    @get:Rule
    val testRule = createComposeRule()

    init {
        TestCase.GivenUserIsType(
            GherkingLamnda1 { userType ->
                if (userType == "Premium") {
                    arguments["userType"] = "Premium"
                } else {
                    arguments["userType"] = "Normal"
                }
                launchScreen()
            }
        )
        TestCase.ThenISeeATextField(
            GherkinLambda2 { textFieldTag, text ->
                testRule.onNodeWithTag(textFieldTag).assertIsDisplayed().assertTextContains(text)
            }
        )
        TestCase.WhenUserIsLoggedIn (
            GherkingLambda0 {
                arguments["isLoggedIn"] = "true"
            } 
        )
    }

    private fun launchScreen() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        launch<MainActivity>(
            Intent(instrumentation.targetContext, MainActivity::class.java)
                .putExtra("isLoggedIn", arguments["isLoggedIn"])
                .putExtra("userType", arguments["userType"])
        )
    }

    private fun <T: Activity> launch(intent: Intent) {
        scenario = ActivityScenario.launch<T>(intent)
    }
}
```
Each steps definition class should implement `En` interface. This is because without that the Cucumber test run would not instantiate that class 
and those step definition would be ignored.

- `testRule: ComposeTestRule` is used to either launch a specific Composable or assert Composables values and state
- `arguments` is a map used to mock certain values and it's passed to the activity through the intent. The launched activity should take care of getting this values and setup the environment to reflect what one wants to test.

## How to setup Cucumber?

### iOS
Follow the manual setup for Cucumberish on [iOS](https://github.com/Ahmed-Ali/Cucumberish/wiki/Install-manually-for-Swift).
Then on the test target you just created, set the FRAMEWORK_SEARCH_PATH as `$(SRCROOT)/../cucumberShared/build/xcode-frameworks/$(CONFIGURATION)/$(SDK_NAME)`.

Once everything is setup, the developer can run ⌘ + U to run the tests from Xcode.


### Android
On Android add the following dependencies in the `android/build.gradle.kts` file:

```
androidTestImplementation(project(":cucumberShared"))
androidTestImplementation("io.cucumber:cucumber-android:4.10.0")
androidTestImplementation("io.cucumber:cucumber-java8:4.8.1")
```
Even though there are major versions available for cucumber-java8, `4.8.1` is the maximum version supported by `cucumber-android`.

Sync and create an `androidTest` folder inside the `android/src`. The `androidTest` folder will contain:
- `assets` folder where the developer can create other sub folder that contains the feature files
- `kotlin` folder where there will be a package like folder structure and 2+ files:
  - the cucumber test runner
  - one or more files where the `TestCase`s will be called.

In order to run the Android test a developer can do that in 2 ways:
- Navigate to the Test runner class and press the play button like it's shown in the screenshot
![TestRunner.jpg](screenshots%2FTestRunner.jpg)

- Or run the `:android:connectedCheck` gradle task 

## Resourceful links
- https://cucumber.io/docs/gherkin/ to learn how the Gherkin syntax works and extract keyword to implement
- https://github.com/cucumber/cucumber-android library for android with example where different approach are used, e.g DI using Hilt, usage of Compose-UI views and Android Views.
- https://github.com/Ahmed-Ali/Cucumberish iOS SDK with examples
- 

## Errors

I've been experiencing a bunch of error since I started working on this project, so I will list them there and beside I will write what I did to circumvent the error or to solve it.

- Running the Android tests from the TestRunner will show no logs or test result whatsoever? 
  - Didn't really find a solution for this issue, but I started running the gradle task directly.

- When creating the iOS test target with the cucumber files, the .m file doesn't find the Cucumber.swift file or its methods. 
  - Be sure that the class and the method you want to use in the .m file are marked with `@objc` annotation and they have `public` visibility.
