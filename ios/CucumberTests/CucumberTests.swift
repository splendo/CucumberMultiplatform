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
        
        
        TestCaseCommonScreenIsVisible { args, userInfo in
            guard let screenName = args?[0] as? String else { return KotlinUnit() }
            
            let text: XCUIElement
            switch(screenName) {
            case "Home screen":
                app.launchEnvironment["isLoggedIn"] = "true"
                text = app.staticTexts["Home screen"]
            case "Login screen":
                app.launchEnvironment["isLoggedIn"] = "false"
                text = app.staticTexts["Login screen"]
                
            default:
                text = app.staticTexts["Fail"]
                XCTFail("Couldn't find \(screenName) screen")
            }
            
            app.launch()
            
            XCTAssert(text.exists(timeout: .long), "Couldn't validate to be in \(screenName)")
            return KotlinUnit()
        }
        
        TestCaseCommonTextIsVisible { args, userInfo in
            guard let textString = args?[0] as? String else { return KotlinUnit() }
            let text = app.staticTexts[textString]
            XCTAssert(text.exists(timeout: .short), "Couldn't find \(text) text")
            return KotlinUnit()
        }
        
        TestCaseCommonTextFieldIsVisible { args, userInfo in
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
        }
        
        TestCaseCommonFillTextField { args, userInfo in
            guard let textFieldText = args?[1] as? String else { return KotlinUnit() }
            guard let text = args?[0] as? String else { return KotlinUnit() }
            let textfield = app.textFields[textFieldText]
            textfield.tap()
            textfield.typeText(text)
            return KotlinUnit()
        }
        
        TestCaseCommonFillPasswordTextField { args, userInfo in
            guard let password = args?[0] as? String else { return KotlinUnit() }
            let textfield = app.secureTextFields["Password"]
            textfield.tap()
            textfield.typeText(password)
            return KotlinUnit()
        }
        
        TestCaseCommonButtonIsVisible { args, userInfo in
            guard let text = args?[0] as? String else { return KotlinUnit() }
            let button = app.buttons[text]
            XCTAssert(button.exists(timeout: .short), "\"\(text)\" button should be visible")
            return KotlinUnit()
        }
        
        TestCaseCommonPressButton { args, userInfo in
            guard let buttonString = args?[0] as? String else { return KotlinUnit() }
            let button = app.buttons[buttonString]
            let link = app.links[buttonString]
            if button.exists(timeout: .medium) && button.isEnabled(timeout: .short) {
                button.tap()
            } else if link.exists(timeout: .medium) && link.isEnabled(timeout: .short) {
                link.tap()
            } else {
                XCTFail("I press \(buttonString) button failed")
            }
            return KotlinUnit()
        }
        
        TestCaseCommonLoggedInEmail { args, userInfo in
            guard let email = args?[0] as? String else { return KotlinUnit() }
            app.launchEnvironment["testEmail"] = email
            return KotlinUnit()
        }
        
        TestCaseCommonNavigateToScreen { args, userInfo in
            guard let screenName = args?[0] as? String else { return KotlinUnit() }
            switch(screenName) {
            case "Home":
                let text = app.staticTexts["Home screen"]
                XCTAssert(text.exists(timeout: .short), "Couldn't validate to be in \(screenName)")
            case "Login":
                let text = app.staticTexts["Login screen"]
                XCTAssert(text.exists(timeout: .short), "Couldn't validate to be in \(screenName)")
            default: XCTFail("Couldn't find \(screenName) screen")
            }
            return KotlinUnit()
        }
        
        let bundle = Bundle(for: CucumberishInitializer.self)
        Cucumberish.executeFeatures(inDirectory: "Features", from: bundle, includeTags: nil, excludeTags: ["ignore"])
    }
}
