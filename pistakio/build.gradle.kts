plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    val target: org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget.() -> Unit = {
        compilations.getByName("main") {
            val xctest by cinterops.creating {
                // Def-file describing the native API.
                defFile(project.file("src/iosMain/xctest.def"))
            }
        }
    }

    iosX64(configure = target)
    iosArm64(configure = target)
    iosSimulatorArm64(configure = target)

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

            }
        }
        val commonTest by getting {
            dependencies {
                val kalugaVersion: String by project
                implementation(kotlin("test"))
                implementation("com.splendo.kaluga:test-utils-base:$kalugaVersion")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.compose.ui:ui-test-junit4:1.4.3")
                implementation("androidx.compose.ui:ui-test-manifest:$1.4.3")
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
    namespace = "com.corrado4eyes.pistakio"
    compileSdk = 33
    defaultConfig {
        minSdk = 29
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

android {
    compileOptions {
        sourceCompatibility =  JavaVersion.VERSION_11
        targetCompatibility =  JavaVersion.VERSION_11
    }
}
