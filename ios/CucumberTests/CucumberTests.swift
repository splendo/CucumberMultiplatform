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
                let assertions = SealedDefinitions.IAmInScreen(
                    application: applicationAdapter,
                    args: args
                ).runAndGetAssertions()
                
                assertAll(assertions: assertions)
            }
            case .iSeeExpectValueStringText: Then(definitionString) { args, userInfo in
                let assertions = SealedDefinitions.ISeeText(
                    application: applicationAdapter,
                    args: args
                ).runAndGetAssertions()
                
                assertAll(assertions: assertions)
            }
            case .iSeeTheExpectValueStringButton: Then(definitionString) { args, userInfo in
                let assertions = SealedDefinitions.ISeeButton(
                    application: applicationAdapter,
                    args: args
                ).runAndGetAssertions()
                
                assertAll(assertions: assertions)
            }
            case .iSeeTheExpectValueStringScreen: Then(definitionString) { args, userInfo in
                let assertions = SealedDefinitions.ISeeScreen(
                    application: applicationAdapter,
                    args: args
                ).runAndGetAssertions()
                
                assertAll(assertions: assertions)
            }
            case .iSeeTheExpectValueStringTextFieldWithTextExpectValueString: Then(definitionString) { args, userInfo in
                let assertions = SealedDefinitions.ISeeTextFieldWithText(
                    application: applicationAdapter,
                    args: args
                ).runAndGetAssertions()
                
                assertAll(assertions: assertions)
            }
            case .iTypeExpectValueStringInTheExpectValueStringTextField: When(definitionString) { args, userInfo in
                let assertions = SealedDefinitions.ITypeTextIntoTextField(application: applicationAdapter, args: args).runAndGetAssertions()
                assertAll(assertions: assertions)
            }
                
            case .iPressTheExpectValueStringButton: When(definitionString) { args, userInfo in
                let assertions = SealedDefinitions.IPressTheButton(application: applicationAdapter, args: args).runAndGetAssertions()
                assertAll(assertions: assertions)
            }
            case .emailIsExpectValueString: Given(definitionString) { args, userInfo in
                let assertions = SealedDefinitions.SetLoggedInUserEmail(application: applicationAdapter, args: args).runAndGetAssertions()
                assertAll(assertions: assertions)
            }
            default:
                XCTFail("unrecognised test case.")
            }
            
            let bundle = Bundle(for: CucumberishInitializer.self)
            Cucumberish.executeFeatures(inDirectory: "Features", from: bundle, includeTags: nil, excludeTags: ["ignore"])
        }
    }
}
