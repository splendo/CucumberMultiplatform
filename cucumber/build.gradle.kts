import org.jetbrains.kotlin.gradle.targets.native.tasks.PodGenTask

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
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
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "cucumber"
        }
        pod("Cucumberish")
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.cucumber:cucumber-java8:4.8.1")
                implementation("io.cucumber:cucumber-junit:4.8.1")
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
    namespace = "com.corrado4eyes.cucumber"
    compileSdk = 33
    defaultConfig {
        minSdk = 29
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

tasks.withType<PodGenTask>().configureEach {
    doLast {
        val xcodeprojFiles = listOf(
            "Pods/Pods.xcodeproj",
            "synthetic.xcodeproj",
        )

        for (xcodeprojFile in xcodeprojFiles) {
            logger.lifecycle("Processing $xcodeprojFile")
            val file = project.buildDir.resolve("cocoapods/synthetic/${family.name}/$xcodeprojFile/project.pbxproj")
            setIosDeploymentTarget(file)
        }
    }
}

fun Project.setIosDeploymentTarget(
    xcodeprojFile: File,
    source: String = "8.0",
    target: String = "14.1",
) {
    val lines = xcodeprojFile.readLines()
    val out = xcodeprojFile.bufferedWriter()
    out.use {
        for (line in lines) {
            out.write(line.replace("IPHONEOS_DEPLOYMENT_TARGET = $source;", "IPHONEOS_DEPLOYMENT_TARGET = $target;"))
            out.write(("\n"))
        }
    }
}

android {
    compileOptions {
        sourceCompatibility =  JavaVersion.VERSION_11
        targetCompatibility =  JavaVersion.VERSION_11
    }
}