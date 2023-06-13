package com.corrado4eyes.cucumberplayground

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform