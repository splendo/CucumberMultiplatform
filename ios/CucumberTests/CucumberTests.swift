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
        }
        
        DefaultGherkinRunner(
            lambdaMaps: [
                TestCase.CommonScreenIsVisible { args, userInfo in
                    app.launch()
                    guard let screenName = args?[0] as? String else { return KotlinUnit() }
                    switch(screenName) {
                    case "Home":
                        let text = app.staticTexts["Home"]
                        XCTAssert(text.exists(timeout: .short), "Couldn't validate to be in \(screenName)")
                    case "Login":
                        let text = app.staticTexts["Login"]
                        XCTAssert(text.exists(timeout: .short), "Couldn't validate to be in \(screenName)")
                    default: XCTFail("Couldn't find \(screenName) screen")
                    }
                    return KotlinUnit()
                },
                TestCase.CommonTitleIsVisible { args, userInfo in
                    guard let textString = args?[0] as? String else { return KotlinUnit() }
                    let text = app.staticTexts[textString]
                    XCTAssert(text.exists(timeout: .short), "Couldn't find \(text) text")
                    return KotlinUnit()
                },
                TestCase.CommonIsUserAuthenticated { args, userInfo in
                    app.launch()
                    guard let textString = args?[0] as? String else { return KotlinUnit() }
                    let isUserLoggedIn = textString == "in" ? true : false
                    if isUserLoggedIn {
                        let text = app.staticTexts["Homepage"]
                        XCTAssert(text.exists(timeout: .short), "Couldn't validate user is logged in")
                    } else {
                        let text = app.staticTexts["Login"]
                        XCTAssert(text.exists(timeout: .short), "Couldn't validate user is logged out")
                    }
                    
                    return KotlinUnit()
                },
                TestCase.LoginCommon.LoginCommonTextFieldIsVisible { args, userInfo in
                    guard let textfieldName = args?[0] as? String else { return KotlinUnit() }
                    guard let textfieldText = args?[1] as? String else { return KotlinUnit() }
                    let textfield: XCUIElement? = {
                        switch (textfieldName){
                        case "Email":
                            return app.textFields[textfieldName]
                        case "Password":
                            return app.secureTextFields[textfieldName]
                        default:
                            return nil
                        }
                    }()
                    if textfield == nil {
                        XCTFail("Couldn't find \"\(textfieldName)\" field")
                        return KotlinUnit()
                    }
                    
                    XCTAssert(textfield?.value as? String == textfieldText, "\"\(textfieldName)\" field text should be \"\(textfield?.value)\"")
                    return KotlinUnit()
                },
                TestCase.LoginFillEmailTextField { args, userInfo in
                    let textfield = app.textFields["Email"]
                    textfield.tap()
                    textfield.typeText("corrado@corrado.com")
                    return KotlinUnit()
                },
                TestCase.LoginFillPasswordTextField { args, userInfo in
                    let textfield = app.secureTextFields["Password"]
                    textfield.tap()
                    textfield.typeText("1234")
                    return KotlinUnit()
                },
                TestCase.CommonButtonIsVisible { args, userInfo in
                    guard let text = args?[0] as? String else { return KotlinUnit() }
                    let button = app.buttons[text]
                    XCTAssert(button.exists(timeout: .short), "\"\(text)\" button should be visible")
                    return KotlinUnit()
                },
                TestCase.LoginPressLoginButton { args, userInfo in
                    guard let buttonName = args?[0] as? String else { return KotlinUnit() }
                    let button = app.buttons[buttonName]
                    let link = app.links[buttonName]
                    if button.exists(timeout: .medium) && button.isEnabled(timeout: .short) {
                        button.tap()
                    } else if link.exists(timeout: .medium) && link.isEnabled(timeout: .short) {
                        link.tap()
                    } else {
                        XCTFail("I press \"\(buttonName)\" button failed")
                    }
                    return KotlinUnit()
                }
            ]
        ).buildFeature()
        
        let bundle = Bundle(for: CucumberishInitializer.self)
        Cucumberish.executeFeatures(inDirectory: "Features", from: bundle, includeTags: nil, excludeTags: nil)
    }
}
