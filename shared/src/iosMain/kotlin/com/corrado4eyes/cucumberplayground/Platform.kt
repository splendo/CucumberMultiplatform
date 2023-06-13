package com.corrado4eyes.cucumberplayground

import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = "this is iOS 🍎"
}

actual fun getPlatform(): Platform = IOSPlatform()