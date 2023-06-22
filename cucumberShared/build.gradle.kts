import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    val target: KotlinNativeTarget.() -> Unit = {
        binaries {
            framework {
                export(project(":shared"))
                export(project(":cucumber"))
                baseName = "shared"

                linkerOpts("-F${projectDir}/../cucumber/build/cocoapods/synthetic/IOS/build/Release-iphonesimulator/Cucumberish", "-framework", "Cucumberish")
                linkerOpts("-F${projectDir}/../cucumber/build/cocoapods/synthetic/IOS/build/Release-iphoneos/Cucumberish", "-framework", "Cucumberish")
                //linker opts tells where to find and link

                //compile opts tells what to compile

            }
        }
    }
    
    iosX64(configure = target)
    iosArm64(configure = target)
    iosSimulatorArm64(configure = target)

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":shared"))
                api(project(":cucumber"))
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
    namespace = "com.corrado4eyes.cucumbershared"
    compileSdk = 33
    defaultConfig {
        minSdk = 29
    }
}