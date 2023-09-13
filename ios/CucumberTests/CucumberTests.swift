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
    
    @objc public class func CucumberishSwiftInit() {
        
        before { _ in
            app = XCUIApplication()
            app.launchArguments.append("test")
        }
        
        for test in Definitions.companion.allCases {
            let definitionString = test.definition.definitionString
            switch test {
            case .screenIsVisible: Given(definitionString) { args, userInfo in
                guard let screenName = args?[0] as? String else { return }
                
                let text: XCUIElement
                switch(screenName) {
                case Strings.shared.homeScreenTag:
                    app.launchEnvironment["isLoggedIn"] = "true"
                    text = app.staticTexts[Strings.shared.homeScreenTitle]
                case Strings.shared.loginScreenTag:
                    app.launchEnvironment["isLoggedIn"] = "false"
                    text = app.staticTexts[Strings.shared.loginScreenTitle]
                    
                default:
                    text = app.staticTexts["Fail"]
                    XCTFail("Couldn't find \(screenName) screen")
                }
                
                app.launch()
                
                XCTAssert(text.exists(timeout: .short), "Couldn't validate to be in \(screenName)")
                return
            }
            case .textIsVisible: Then(definitionString){ args, userInfo in
                guard let textString = args?[0] as? String else { return }
                let text = app.staticTexts[textString]
                XCTAssert(text.exists(timeout: .short), "Couldn't find \(text) text")
                return
            }
            case .buttonIsVisible: Then(definitionString) { args, userInfo in
                guard let text = args?[0] as? String else { return }
                let button = app.buttons[text]
                XCTAssert(button.exists(timeout: .short), "\"\(text)\" button should be visible")
                return
            }
            case .navigateToScreen: Then(definitionString) { args, userInfo in
                guard let screenName = args?[0] as? String else { return }
                switch(screenName) {
                case Strings.shared.homeScreenTag:
                    let text = app.staticTexts[Strings.shared.homeScreenTitle]
                    XCTAssert(text.exists(timeout: .short), "Couldn't validate to be in \(screenName)")
                case Strings.shared.loginScreenTag:
                    let text = app.staticTexts[Strings.shared.loginScreenTitle]
                    XCTAssert(text.exists(timeout: .short), "Couldn't validate to be in \(screenName)")
                default: XCTFail("Couldn't find \(screenName) screen")
                }
                return
            }
            case .textfieldIsVisible: Then(definitionString) { args, userInfo in
                guard let textfieldName = args?[0] as? String else { return }
                guard let textfieldText = args?[1] as? String else { return }
                let textfield: XCUIElement? = {
                    switch (textfieldName){
                    case Strings.shared.emailTextFieldTag:
                        return app.textFields[textfieldName]
                    case Strings.shared.passwordTextFieldTag:
                        return app.secureTextFields[textfieldName]
                    default:
                        return nil
                    }
                }()
                if textfield == nil {
                    XCTFail("Couldn't find \"\(textfieldName)\" field")
                    return
                }
                
                XCTAssert(textfield?.value as? String == textfieldText, "\"\(textfieldName)\" field text should be \"\(textfield?.value)\"")
                return
            }
            case .fillTextfield: Then(definitionString) { args, userInfo in
                guard let textfieldName = args?[1] as? String else { return }
                guard let textfieldText = args?[0] as? String else { return }
                let textfield = app.textFields[textfieldName]
                textfield.tap()
                textfield.typeText(textfieldText)
                return
            }
            case .fillSecureTextfield: Then(definitionString) { args, userInfo in
                guard let textfieldName = args?[1] as? String else { return }
                guard let textfieldText = args?[0] as? String else { return }
                let textfield = app.secureTextFields[textfieldName]
                textfield.tap()
                textfield.typeText(textfieldText)
                return
            }
                
            case .pressButton: Then(definitionString) { args, userInfo in
                guard let buttonName = args?[0] as? String else { return }
                let button = app.buttons[buttonName]
                let link = app.links[buttonName]
                
                if button.exists(timeout: .medium) && button.isEnabled(timeout: .short) {
                    button.tap()
                } else if link.exists(timeout: .medium) && link.isEnabled(timeout: .short) {
                    link.tap()
                } else {
                    XCTFail("I press Login button failed")
                }
                return
            }
            case .userIsLoggedIn: Given(definitionString) { args, userInfo in
                guard let email = args?[0] as? String else { return }
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
