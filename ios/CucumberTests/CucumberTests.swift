//
//  CucumberTests.swift
//  CucumberTests
//
//  Created by Corrado Quattrocchi on 12/06/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import XCTest
import Cucumberish

@objc public class CucumberishInitializer: NSObject {
    static var app: XCUIApplication!
    static var applicationAdapter: ApplicationAdapter!
    
    @objc public class func assertAll(assertions: [AssertionResult]) {
        for assertion in assertions {
            XCTAssert(assertion is AssertionResult.Success)
        }
    }
    
    @objc public class func CucumberishSwiftInit() {
        beforeStart {
            app = XCUIApplication()
            app.launchArguments.append("test")
            applicationAdapter = DefaultApplicationAdapter(app: app)
        }
        
        afterFinish {
            applicationAdapter.tearDown()
        }
        
        for test in Definitions.companion.allCases {
            let definitionString = test.definition.definitionString
            switch test {
            case .iAmInTheExpectValueStringScreen: Given(definitionString) { args, userInfo in
                let assertions = AppDefinitions.CrossPlatformIAmInScreen(
                    launchScreenName: nil,
                    application: applicationAdapter,
                    args: args
                ).runAndGetAssertions()
                
                assertAll(assertions: assertions)
            }
            case .iSeeExpectValueStringText: Then(definitionString) { args, userInfo in
                let assertions = AppDefinitions.CrossPlatformISeeText(
                    application: applicationAdapter,
                    args: args
                ).runAndGetAssertions()
                
                assertAll(assertions: assertions)
            }
            case .iSeeTheExpectValueStringButton: Then(definitionString) { args, userInfo in
                let assertions = AppDefinitions.CrossPlatformISeeButton(
                    application: applicationAdapter,
                    args: args
                ).runAndGetAssertions()
                
                assertAll(assertions: assertions)
            }
            case .iSeeTheExpectValueStringScreen: Then(definitionString) { args, userInfo in
                let assertions = AppDefinitions.CrossPlatformISeeScreen(
                    application: applicationAdapter,
                    args: args
                ).runAndGetAssertions()
                
                assertAll(assertions: assertions)
            }
            case .iSeeTheExpectValueStringTextFieldWithTextExpectValueString: Then(definitionString) { args, userInfo in
                let assertions = AppDefinitions.CrossPlatformISeeTextFieldWithText(
                    application: applicationAdapter,
                    args: args
                ).runAndGetAssertions()
                
                assertAll(assertions: assertions)
            }
            case .iTypeExpectValueStringInTheExpectValueStringTextField: When(definitionString) { args, userInfo in
                let assertions = AppDefinitions.CrossPlatformITypeTextIntoTextField(application: applicationAdapter, args: args).runAndGetAssertions()
                assertAll(assertions: assertions)
            }
                
            case .iPressTheExpectValueStringButton: When(definitionString) { args, userInfo in
                let assertions = AppDefinitions.CrossPlatformIPressTheButton(application: applicationAdapter, args: args).runAndGetAssertions()
                assertAll(assertions: assertions)
            }
            case .emailIsExpectValueString: Given(definitionString) { args, userInfo in
                let assertions = AppDefinitions.CrossPlatformSetLoggedInUserEmail(application: applicationAdapter, args: args).runAndGetAssertions()
                assertAll(assertions: assertions)
            }
            case .iSeeExpectValueStringInTheScrollview: Then(definitionString) { args, userInfo in
                guard let scrollViewItemIndex = args?[0] as? String else { return }
                app.descendants(matching: .any)
                    .matching(identifier: Strings.ScrollViewTag.shared.homeScrollView)
                    .element.swipeUp()
                let scrollItem = app.staticTexts[scrollViewItemIndex]
                XCTAssert(scrollItem.waitForExistence(timeout: 0.1))
            }
                
            
            default:
                XCTFail("unrecognised test case.")
            }
            
            let bundle = Bundle(for: CucumberishInitializer.self)
            Cucumberish.executeFeatures(inDirectory: "Features", from: bundle, includeTags: nil, excludeTags: ["ignore"])
        }
    }
}
