plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
}

android {
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

kotlin {
    val kalugaVersion: String by project
    val koinVersion: String by project

    val target: org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget.() -> Unit = {
        binaries {
            framework {
                baseName = "shared"

                export("com.splendo.kaluga:alerts:$kalugaVersion")
                export("com.splendo.kaluga:hud:$kalugaVersion")
                export("com.splendo.kaluga:architecture:$kalugaVersion")
                export("com.splendo.kaluga:keyboard:$kalugaVersion")
                export("com.splendo.kaluga:resources:$kalugaVersion")
                export("com.splendo.kaluga:base:$kalugaVersion")

                getTest("DEBUG").apply {
                    freeCompilerArgs = freeCompilerArgs + "-e"
                    freeCompilerArgs = freeCompilerArgs + "com.splendo.kaluga.test.mainBackground"
                }
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
                api("io.insert-koin:koin-core:$koinVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                api(kotlin("test"))
                api(kotlin("test-junit"))
                api("com.splendo.kaluga:test-utils:$kalugaVersion")
                api("io.insert-koin:koin-test:$koinVersion")
            }
        }
        val androidMain by getting {
            dependencies {
                api("io.insert-koin:koin-android:$koinVersion")
            }
        }
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
    namespace = "com.splendo.cucumberplayground"
    compileSdk = 33
    defaultConfig {
        minSdk = 29
    }
    compileOptions {
        sourceCompatibility =  JavaVersion.VERSION_11
        targetCompatibility =  JavaVersion.VERSION_11
    }
}
