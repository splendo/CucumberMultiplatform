plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.3.0-alpha07").apply(false)
    id("com.android.library").version("8.3.0-alpha07").apply(false)
    kotlin("android").version("1.9.10").apply(false)
    kotlin("multiplatform").version("1.9.10").apply(false)
}

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        classpath(kotlin("serialization"))
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
