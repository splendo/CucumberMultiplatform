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
    
    @objc public class func CucumberishSwiftInit() {
        beforeStart {
            app = XCUIApplication()
            applicationAdapter = DefaultApplicationAdapter(app: app)
            app.launchArguments.append("test")
        }
        
        afterFinish {
            applicationAdapter.tearDown()
        }
        
        for test in Definitions.companion.allCases {
            let definitionString = test.definition.definitionString
            switch test {
            case .iAmInTheExpectValueStringScreen: Given(definitionString) { args, userInfo in
                guard let screenTitle = ApplicationAdapterCompanion.shared.getArgument(arguments: args, index: 0) else { return }

                let screenTag: String
                switch screenTitle {
                case Strings.ScreenTitle.shared.home:
                    app.launchEnvironment["isLoggedIn"] = "true"
                    screenTag = Strings.ScreenTag.shared.home
                case Strings.ScreenTitle.shared.login:
                    app.launchEnvironment["isLoggedIn"] = "false"
                    screenTag = Strings.ScreenTag.shared.login
                    
                default:
                    screenTag = "Fail"
                    XCTFail("Couldn't find \(screenTag) screen")
                }
                
                applicationAdapter.launch(identifier: nil, arguments: app.launchEnvironment)
                
                let element = applicationAdapter.findView(tag: screenTag)

                element.assert(assertionResult: element.waitExists(timeout: .short_), message: "Couldn't validate to be in \(screenTitle)")
            }
            case .iSeeExpectValueStringText: Then(definitionString) { args, userInfo in
                guard let textViewTitle = ApplicationAdapterCompanion.shared.getArgument(arguments: args, index: 0) else { return }
                let element = applicationAdapter.findView(tag: textViewTitle)
                
                element.assert(assertionResult: element.waitExists(timeout: .short_), message: "Couldn't find \(element) text")
            }
            case .iSeeTheExpectValueStringButton: Then(definitionString) { args, userInfo in
                guard let buttonTitle = ApplicationAdapterCompanion.shared.getArgument(arguments: args, index: 0) else { return }
                
                let buttonTag: String
                switch buttonTitle {
                case Strings.ButtonTitle.shared.login:
                    buttonTag = Strings.ButtonTag.shared.login
                case Strings.ButtonTitle.shared.logout:
                    buttonTag = Strings.ButtonTag.shared.logout
                default:
                    buttonTag = "Fail"
                    XCTFail("Couldn't find \(buttonTitle) button")
                }
                
                let element = applicationAdapter.findView(tag: buttonTag)
                element.assert(assertionResult: element.waitExists(timeout: .short_), message: "Couldn't find \(buttonTitle) button")
                
                element.assert(assertionResult: element.isButton(), message: "Couldn't validate that \(buttonTitle) is a button")
            }
            case .iSeeTheExpectValueStringScreen: Then(definitionString) { args, userInfo in
                guard let screenTitle = ApplicationAdapterCompanion.shared.getArgument(arguments: args, index: 0) else { return }
                
                let screenTag: String
                switch screenTitle {
                case Strings.ScreenTitle.shared.home:
                    screenTag = Strings.ScreenTag.shared.home
                case Strings.ScreenTitle.shared.login:
                    screenTag = Strings.ScreenTag.shared.login
                default:
                    screenTag = "Fail"
                    XCTFail("Couldn't find \(screenTitle) screen")
                }
                
                let element = applicationAdapter.findView(tag: screenTag)
                let elementExists = element.waitExists(timeout: .long_)
                element.assert(assertionResult: elementExists, message: "Couldn't find \(screenTag) screen")
            }
            case .iSeeTheExpectValueStringTextFieldWithTextExpectValueString: Then(definitionString) { args, userInfo in
                guard let textFieldTag = ApplicationAdapterCompanion.shared.getArgument(arguments: args, index: 0) else { return }
                guard let textFieldText = ApplicationAdapterCompanion.shared.getArgument(arguments: args, index: 1) else { return }
                
                let element = applicationAdapter.findView(tag: textFieldTag)
                
                element.assert(assertionResult: element.waitExists(timeout: .short_), message: "Couldn't find \(textFieldTag) field")
                
                element.assert(assertionResult: element.isHintEqualTo(value: textFieldText, contains: true), message: "TextField value doesn't match \(textFieldText)")
            }
            case .iTypeExpectValueStringInTheExpectValueStringTextField: When(definitionString) { args, userInfo in
                guard let textfieldTag = ApplicationAdapterCompanion.shared.getArgument(arguments: args, index: 1) else { return }
                guard let textfieldText = ApplicationAdapterCompanion.shared.getArgument(arguments: args, index: 0) else { return }
                
                let element = applicationAdapter.findView(tag: textfieldTag)
                element.typeText(text: textfieldText)
            }
            case .iTypeExpectValueStringInTheExpectValueStringSecureTextField: When(definitionString) { args, userInfo in
                guard let textfieldTag = ApplicationAdapterCompanion.shared.getArgument(arguments: args, index: 1) else { return }
                guard let textfieldText = ApplicationAdapterCompanion.shared.getArgument(arguments: args, index: 0) else { return }
                
                let element = applicationAdapter.findView(tag: textfieldTag)
                element.typeText(text: textfieldText)
            }
                
            case .iPressTheExpectValueStringButton: When(definitionString) { args, userInfo in
                guard let buttonTitle = ApplicationAdapterCompanion.shared.getArgument(arguments: args, index: 0) else { return }
                let buttonTag: String
                switch buttonTitle {
                case Strings.ButtonTitle.shared.login:
                    buttonTag = Strings.ButtonTag.shared.login
                case Strings.ButtonTitle.shared.logout:
                    buttonTag = Strings.ButtonTag.shared.logout
                default:
                    buttonTag = "Fail"
                    XCTFail("I press the \(buttonTitle) button failed")
                }
                
                let element = applicationAdapter.findView(tag: buttonTag)
                element.tap()
            }
            case .emailIsExpectValueString: Given(definitionString) { args, userInfo in
                guard let email = ApplicationAdapterCompanion.shared.getArgument(arguments: args, index: 0) else { return }
                app.launchEnvironment["testEmail"] = email
                return
            }
            default:
                XCTFail("unrecognised test case.")
            }
            
            let bundle = Bundle(for: CucumberishInitializer.self)
            Cucumberish.executeFeatures(inDirectory: "Features", from: bundle, includeTags: nil, excludeTags: ["ignore"])
        }
    }
}
