import org.jetbrains.kotlin.gradle.targets.native.tasks.PodGenTask

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

kotlin {

    val target: org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget.() -> Unit =
        {
            val cinteropDir: String by project
            val iosDependencies: List<String> by project
            val architectureFromTarget: (org.jetbrains.kotlin.konan.target.KonanTarget, String) -> String by project
            val nativeFrameworkPaths = listOf(
                projectDir.resolve("$cinteropDir/Carthage/Build/iOS")
            ).plus(
                iosDependencies.map { iosDependency ->
                    projectDir.resolve(
                        "$cinteropDir/Carthage/Build/$iosDependency.xcframework/${architectureFromTarget(konanTarget, iosDependency)}"
                    )
                }
            )

            binaries {
                getTest("DEBUG").apply {
                    linkerOpts(nativeFrameworkPaths.map { "-F$it" })
                    linkerOpts("-framework", "Cucumberish")
                    linkerOpts(
                        "-rpath",
                        nativeFrameworkPaths.first { it.path.contains("Cucumberish") }.absolutePath
                    )
                    linkerOpts("-ObjC")
                }
            }

            compilations.getByName("main") {
                cinterops.create("XCTest") {
                    val xcTestDefPath = projectDir.resolve("$cinteropDir/XCTest.def")
                    defFile(xcTestDefPath.absolutePath)
                    compilerOpts("-I/Applications/Xcode.app/Contents/Developer/Platforms/iPhoneOS.platform/Developer/Library/Frameworks/XCTest.framework/Headers")
                }
                cinterops.create("Cucumberish") {
                    compilerOpts(nativeFrameworkPaths.map { "-F$it" })
                    extraOpts = listOf("-compiler-option", "-DNS_FORMAT_ARGUMENT(A)=", "-verbose")
                }
            }
        }

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64(target)
    iosArm64(target)
    iosSimulatorArm64(target)
    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"

        podfile = project.file("../ios/Podfile")
        framework {
            baseName = "shared"
        }
        pod("AFNetworking")
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
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

//tasks.withType<PodGenTask>().configureEach {
//    doLast {
//        val podsConfigurationSource = "{PODS_CONFIGURATION_BUILD_DIR}"
//        val podsConfiguration = "\$$podsConfigurationSource"
//       podfile.get().also {
//           it.appendText("\n")
//           it.appendText("""
//           post_install do |installer|
//             installer.pods_project.targets.each do |target|
//               if target.name == "Pods-ios"
//                 puts "Updating #{target.name} to exclude Catalyst incompatible frameworks"
//                 target.build_configurations.each do |config|
//                   xcconfig_path = config.base_configuration_reference.real_path
//                   xcconfig = File.read(xcconfig_path)
//                   xcconfig.sub!('-framework "Cucumberish"', '') //Remove unsupported frameworks in OTHER_LDFLAGS
//                   xcconfig.sub!('"$podsConfiguration/Cucumberish/Cucumberish/Cucumberish.framework/Headers"', '') // Remove in FRAMEWORK_SEARCH_PATHS, you need to figure out this path for each library
//                   new_xcconfig = xcconfig + 'OTHER_LDFLAGS[sdk=iphone*] = ${'$'}(inherited) + "\n" + 'FRAMEWORK_SEARCH_PATHS[sdk=iphone*] =  ${'$'}(inherited)' //Not only OTHER_LDFLAGS, but also FRAMEWORK_SEARCH_PATHS needs same trick here.
//                   File.open(xcconfig_path, "w") { |file| file << new_xcconfig }
//               end
//             end
//           end
//        """.trimIndent())
//       }
//    }
//}

android {
    namespace = "com.corrado4eyes.cucumberplayground"
    compileSdk = 33
    defaultConfig {
        minSdk = 29
    }
}
