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
        
        
        
        for testCase in AppDefinitions.companion.allCases {
            let definitionString = testCase.definition.definitionString
            switch onEnum(of: testCase) {

            case .crossPlatform(let crossPlatformTestCase):
                switch onEnum(of: crossPlatformTestCase) {
                case .iAmInScreen(_):
                    Given(definitionString) { args, userInfo in
                        let assertions = AppDefinitions.CrossPlatformIAmInScreen(
                            launchScreenName: nil,
                            application: applicationAdapter,
                            args: args
                        ).runAndGetAssertions()

                        assertAll(assertions: assertions)
                    }
                case .iPressTheButton(_):
                    When(definitionString) { args, userInfo in
                        let assertions = AppDefinitions.CrossPlatformIPressTheButton(application: applicationAdapter, args: args).runAndGetAssertions()
                        assertAll(assertions: assertions)
                    }
                case .iSeeButton(_):
                    Then(definitionString) { args, userInfo in
                        let assertions = AppDefinitions.CrossPlatformISeeButton(
                            application: applicationAdapter,
                            args: args
                        ).runAndGetAssertions()

                        assertAll(assertions: assertions)
                    }
                case .iSeeScreen(_):
                    Then(definitionString) { args, userInfo in
                        let assertions = AppDefinitions.CrossPlatformISeeScreen(
                            application: applicationAdapter,
                            args: args
                        ).runAndGetAssertions()

                        assertAll(assertions: assertions)
                    }
                case .iSeeText(_):
                    Then(definitionString) { args, userInfo in
                        let assertions = AppDefinitions.CrossPlatformISeeText(
                            application: applicationAdapter,
                            args: args
                        ).runAndGetAssertions()

                        assertAll(assertions: assertions)
                    }
                case .iSeeTextFieldWithText(_):
                    Then(definitionString) { args, userInfo in
                        let assertions = AppDefinitions.CrossPlatformISeeTextFieldWithText(
                            application: applicationAdapter,
                            args: args
                        ).runAndGetAssertions()

                        assertAll(assertions: assertions)
                    }
                case .iTypeTextIntoTextField(_):
                    When(definitionString) { args, userInfo in
                        let assertions = AppDefinitions.CrossPlatformITypeTextIntoTextField(application: applicationAdapter, args: args).runAndGetAssertions()
                        assertAll(assertions: assertions)
                    }
                case .setLoggedInUserEmail(_):
                    Given(definitionString) { args, userInfo in
                        let assertions = AppDefinitions.CrossPlatformSetLoggedInUserEmail(application: applicationAdapter, args: args).runAndGetAssertions()
                        assertAll(assertions: assertions)
                    }
                }
            case .platform(let platformTestCase):
                switch onEnum(of: platformTestCase) {
                case .iSeeValueInScrollView(_):
                    Then(definitionString) { args, userInfo in
                        guard let scrollViewItemIndex = args?[0] as? String else { return }
                        // Platform implementation
                        app.descendants(matching: .any)
                            .matching(identifier: Strings.ScrollViewTag.shared.homeScrollView)
                            .element.swipeUp()
                        let scrollItem = app.staticTexts[scrollViewItemIndex]
                        XCTAssert(scrollItem.waitForExistence(timeout: 0.1))

                        // Cross-platform implementation
//                        let element = applicationAdapter.findView(tag: Strings.ScrollViewTag.shared.homeScrollView)
//                        element.swipeUntilIndex(index: Int32(scrollViewItemIndex)!, velocity: 200.0)
//                        let text = applicationAdapter.findView(tag: scrollViewItemIndex)
//                        assertAll(assertions: [text.exists()])
                    }
                }
            }
        }
        
        let bundle = Bundle(for: CucumberishInitializer.self)
        Cucumberish.executeFeatures(inDirectory: "Features", from: bundle, includeTags: nil, excludeTags: ["ignore"])
    }
}
