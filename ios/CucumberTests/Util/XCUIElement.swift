//
//  XCUIElement.swift
//  CucumberTests
//
//  Created by Corrado Quattrocchi on 13/06/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import XCTest

extension XCUIElement {
    func exists(timeout: Timeout) ->  Bool {
        let predicate = NSPredicate(format: "exists == true")
        let expectation = XCTestCase().expectation(for: predicate, evaluatedWith: self)
        let result = XCTWaiter().wait(for: [expectation], timeout: timeout.rawValue)
        return result == .completed
    }
    
    func isEnabled(timeout: Timeout) -> Bool {
        let predicate = NSPredicate(format: "enabled == true")
        let expectation = XCTestCase().expectation(for: predicate, evaluatedWith: self)
        let result = XCTWaiter().wait(for: [expectation], timeout: timeout.rawValue)
        return result == .completed
    }
}
