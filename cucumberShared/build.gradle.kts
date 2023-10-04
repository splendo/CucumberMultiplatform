import co.touchlab.skie.configuration.DefaultArgumentInterop
import co.touchlab.skie.configuration.EnumInterop
import co.touchlab.skie.configuration.ExperimentalFeatures
import co.touchlab.skie.configuration.FlowInterop
import co.touchlab.skie.configuration.SealedInterop
import co.touchlab.skie.configuration.SuspendInterop
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("co.touchlab.skie") version "0.5.0"
}

kotlin {

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    val target: KotlinNativeTarget.() -> Unit = {
        binaries {
            framework {
                transitiveExport = true
                export(project(":shared"))
                export("com.splendo.cucumber:cucumber:0.1.0")
                export("com.splendo.kilka:kilka:0.1.0")
                baseName = "shared"
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
                api("com.splendo.kilka:kilka:0.1.0")
                api("com.splendo.cucumber:cucumber:0.1.0")

                implementation("co.touchlab.skie:configuration-annotations:0.5.0")
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
    namespace = "com.splendo.cucumbershared"
    compileSdk = 33
    defaultConfig {
        minSdk = 29
    }
    compileOptions {
        sourceCompatibility =  JavaVersion.VERSION_11
        targetCompatibility =  JavaVersion.VERSION_11
    }
}

skie {
    analytics {
        enabled.set(false)
    }

    features {
        group {
            DefaultArgumentInterop.Enabled(false)
            EnumInterop.Enabled(false)
            ExperimentalFeatures.Enabled(false)
            FlowInterop.Enabled(false)
            SealedInterop.Enabled(false)
            SuspendInterop.Enabled(false)
        }
    }
}
