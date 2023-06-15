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
                    default: XCTFail("Couldn't find \(screenName) screen")
                    }
                    return KotlinUnit()
                },
                TestCase.CommonTitleIsVisible { args, userInfo in
                    guard let textString = args?[0] as? String else { return KotlinUnit() }
                    let text = app.staticTexts[textString]
                    XCTAssert(text.exists(timeout: .short), "Couldn't find \(text) text")
                    return KotlinUnit()
                }
            ]
        ).buildFeature()
        
        let bundle = Bundle(for: CucumberishInitializer.self)
        Cucumberish.executeFeatures(inDirectory: "Features", from: bundle, includeTags: nil, excludeTags: nil)
    }
}
