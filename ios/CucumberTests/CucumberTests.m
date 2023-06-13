//
//  CucumberTests.m
//  CucumberTests
//
//  Created by Corrado Quattrocchi on 12/06/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

#import "CucumberTests-Swift.h"

__attribute__((constructor))
void CucumberishInit(void)
{
    [CucumberishInitializer CucumberishSwiftInit];
}
