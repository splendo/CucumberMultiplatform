import org.jetbrains.kotlin.gradle.targets.native.tasks.PodGenTask

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    val kalugaVersion: String by project

    val target: org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget.() -> Unit = {
        binaries {
            framework {
                baseName = "shared"

                export("com.splendo.kaluga:alerts:$kalugaVersion")
                export("com.splendo.kaluga:architecture:$kalugaVersion")
                export("com.splendo.kaluga:hud:$kalugaVersion")
                export("com.splendo.kaluga:keyboard:$kalugaVersion")
                export("com.splendo.kaluga:resources:$kalugaVersion")
            }
        }
    }

    iosX64(configure = target)
    iosArm64(configure = target)
    iosSimulatorArm64(configure = target)

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
                api("com.splendo.kaluga:alerts:$kalugaVersion")
                api("com.splendo.kaluga:architecture:$kalugaVersion")
                api("com.splendo.kaluga:base:$kalugaVersion")
                api("com.splendo.kaluga:hud:$kalugaVersion")
                api("com.splendo.kaluga:keyboard:$kalugaVersion")
                api("com.splendo.kaluga:resources:$kalugaVersion")
                api("com.splendo.kaluga:service:$kalugaVersion")
                api("com.splendo.kaluga:system:$kalugaVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidUnitTest by getting

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }

        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.corrado4eyes.cucumberplayground"
    compileSdk = 33
    defaultConfig {
        minSdk = 29
    }
    compileOptions {
        sourceCompatibility =  JavaVersion.VERSION_11
        targetCompatibility =  JavaVersion.VERSION_11
    }
}
