//
//  Node+Extensions.swift
//  CucumberTests
//
//  Created by Corrado Quattrocchi on 15/09/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared
import XCTest

extension Node {
    func assert(assertionResult: AssertionResult, message: String) {
        XCTAssert(assertionResult is AssertionResult.Success, message)
    }
}
