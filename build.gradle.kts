import org.apache.tools.ant.taskdefs.condition.Os
import org.jetbrains.kotlin.gradle.tasks.CInteropProcess
import org.jetbrains.kotlin.konan.target.KonanTarget

plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.1.0-beta04").apply(false)
    id("com.android.library").version("8.1.0-beta04").apply(false)
    kotlin("android").version("1.8.20").apply(false)
    kotlin("multiplatform").version("1.8.20").apply(false)
}

buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.1.0-rc01")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
