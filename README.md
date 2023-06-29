# Cucumber Playground

A wrapper for Android and iOS Cucumber SDKs + example.

## Structure
The project is divided into 3 kotlin module + 1 ios project.
- `shared` represents the business logic of the example app.
- `cucumber` its the wrapper for the Cucumber sdk.
- `cucumberShared` its a module that includes both shared and cucumber modules. This is where `TestCase`s will be declared since it can access both module references. Also it exists because Cucumber platform SDK uses some test dependencies that should not be used from a main target (e.g. on iOS `XCTest`) otherwise it will lead to Runtime exceptions.

On Android `cucumberShared` can be imported on the `androidTest` target using `androidTestImplementation(project(":cucumberShared"))`. 
While on iOS `cucumberShared` will export `cucumber` module and the `Cucumberish` SDK by using the files created by the cocoapods plugin on the `cucumber` module.
Both `shared` and `cucumberShared` will create a framework called `shared` so that there won't be mismatches with the types on iOS. Its up to the developer to manually change the `FRAMEWORK_SEARCH_PATH` for the iOS targets so that the main target will point to the `projectDir/shared...` while the test target that contains the Cucumber test runner will set the FRAMEWORK_SEARCH_PATH as `projectDir/cucumberShared/...`  

## How does it work?
The project is based on a setup where the common sourceset wraps the 3 most used Gherkin keyword: Given, When and Then.

Define a `TestCase`s on each platform. A `TestCase` extends a `GherkinTestCase` and represents a kotlin like representation of a Gherkin step
with its generic regex, the action to be done when that line is read and what step it is. A `GherkinTestCase` is an interface with a `step` type, a `lambda`.

Lets take into example this 2 steps
```gherkin
When User is logged in
Given I am a "premium" user
Then I see the "Premium" text

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

While its easy to read and write a step type and the regex it represents, when talking about iOS it would kind of hard to write everything in kotlin/native.
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
- `arguments` is a map used to mock certain values and its passed to the activity through the intent. The launched activity should take care of getting this values and setup the environment to reflect what one wants to test.
