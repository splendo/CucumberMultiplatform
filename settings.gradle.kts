pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CucumberPlayground"
include(":android")
include(":shared")
include(":cucumberShared")

include(":pistakio")
include(":cucumber")
