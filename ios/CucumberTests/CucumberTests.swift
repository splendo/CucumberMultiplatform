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
        
        TestCaseCommonScreenIsVisible(lambda: GherkinLambda1 { args, userInfo in
            guard let screenName = args?[0] as? String else { return }
            
            let text: XCUIElement
            switch(screenName) {
            case "Home":
                app.launchEnvironment["isLoggedIn"] = "true"
                text = app.staticTexts["Home screen"]
            case "Login":
                app.launchEnvironment["isLoggedIn"] = "false"
                text = app.staticTexts["Login screen"]
                
            default:
                text = app.staticTexts["Fail"]
                XCTFail("Couldn't find \(screenName) screen")
            }
            
            app.launch()
            
            XCTAssert(text.exists(timeout: .short), "Couldn't validate to be in \(screenName)")
            return
        })
        
        Then(TestCaseCommonTitleIsVisible.companion.expectedString) { args, userInfo in
            guard let textString = args?[0] as? String else { return }
            print(" 🔥 I should be inside TestCaseCommonTitleIsVisible 🔥")
            let text = app.staticTexts[textString]
            XCTAssert({
                let predicate = NSPredicate(format: "exists == true")
                let expectation = XCTestCase().expectation(for: predicate, evaluatedWith: self)
                let result = XCTWaiter().wait(for: [expectation], timeout: Timeout.short.rawValue)
                return result == .completed
            }(), "Couldn't find \(text) text")
            return
        }
        
        TestCaseCommonTitleIsVisible (lambda: GherkinLambda1 { args, userInfo in
            guard let textString = args?[0] as? String else { return }
            print(" 🔥 I should be inside TestCaseCommonTitleIsVisible 🔥")
            let text = app.staticTexts[textString]
            XCTAssert({
                let predicate = NSPredicate(format: "exists == true")
                let expectation = XCTestCase().expectation(for: predicate, evaluatedWith: self)
                let result = XCTWaiter().wait(for: [expectation], timeout: Timeout.short.rawValue)
                return result == .completed
            }(), "Couldn't find \(text) text")
            return
        })
        
//        TestCaseLoginCommonTextFieldIsVisible { args, userInfo in
//            guard let textfieldName = args?[0] as? String else { return KotlinUnit() }
//            guard let textfieldText = args?[1] as? String else { return KotlinUnit() }
//            let textfield: XCUIElement? = {
//                switch (textfieldName){
//                case "Email":
//                    return app.textFields[textfieldName]
//                case "Password":
//                    return app.secureTextFields[textfieldName]
//                default:
//                    return nil
//                }
//            }()
//            if textfield == nil {
//                XCTFail("Couldn't find \"\(textfieldName)\" field")
//                return KotlinUnit()
//            }
//
//            XCTAssert(textfield?.value as? String == textfieldText, "\"\(textfieldName)\" field text should be \"\(textfield?.value)\"")
//            return KotlinUnit()
//        }
//
//        TestCaseLoginFillEmailTextField { args, userInfo in
//            guard let email = args?[0] as? String else { return KotlinUnit() }
//            let textfield = app.textFields["Email"]
//            textfield.tap()
//            textfield.typeText(email)
//            return KotlinUnit()
//        }
//
//        TestCaseLoginFillPasswordTextField { args, userInfo in
//            guard let password = args?[0] as? String else { return KotlinUnit() }
//            let textfield = app.secureTextFields["Password"]
//            textfield.tap()
//            textfield.typeText(password)
//            return KotlinUnit()
//        }
//
//        TestCaseCommonButtonIsVisible { args, userInfo in
//            guard let text = args?[0] as? String else { return KotlinUnit() }
//            let button = app.buttons[text]
//            XCTAssert(button.exists(timeout: .short), "\"\(text)\" button should be visible")
//            return KotlinUnit()
//        }
//
//        TestCaseLoginPressLoginButton { args, userInfo in
//            let button = app.buttons["Login"]
//            let link = app.links["Login"]
//            if button.exists(timeout: .medium) && button.isEnabled(timeout: .short) {
//                button.tap()
//            } else if link.exists(timeout: .medium) && link.isEnabled(timeout: .short) {
//                link.tap()
//            } else {
//                XCTFail("I press Login button failed")
//            }
//            return KotlinUnit()
//        }
//
//        TestCaseHomeLoggedInEmail { args, userInfo in
//            guard let email = args?[0] as? String else { return KotlinUnit() }
//            app.launchEnvironment["testEmail"] = email
//            return KotlinUnit()
//        }
//
//        TestCaseCommonNavigateToScreen { args, userInfo in
//            guard let screenName = args?[0] as? String else { return KotlinUnit() }
//            switch(screenName) {
//            case "Home":
//                let text = app.staticTexts["Home screen"]
//                XCTAssert(text.exists(timeout: .short), "Couldn't validate to be in \(screenName)")
//            case "Login":
//                let text = app.staticTexts["Login screen"]
//                XCTAssert(text.exists(timeout: .short), "Couldn't validate to be in \(screenName)")
//            default: XCTFail("Couldn't find \(screenName) screen")
//            }
//            return KotlinUnit()
//        }
//
//        TestCaseHomePressLogoutButton { args, userInfo in
//            let button = app.buttons["Logout"]
//            let link = app.links["Logout"]
//            if button.exists(timeout: .medium) && button.isEnabled(timeout: .short) {
//                button.tap()
//            } else if link.exists(timeout: .medium) && link.isEnabled(timeout: .short) {
//                link.tap()
//            } else {
//                XCTFail("I press Logout button failed")
//            }
//            return KotlinUnit()
//        }
        
        let bundle = Bundle(for: CucumberishInitializer.self)
        Cucumberish.executeFeatures(inDirectory: "Features", from: bundle, includeTags: nil, excludeTags: ["ignore"])
    }
}
