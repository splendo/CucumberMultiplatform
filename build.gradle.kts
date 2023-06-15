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

val cinteropDir: String by extra("src/nativeInterop/cinterop")
val iosDependencies: List<String> by extra(
    listOf("Cucumberish")
)
val iosCartfileDependencies: List<String> = listOf("Cucumberish")

val iOs64BitOnlyCpuDependencies: List<String> by extra {
    listOf("Cucumberish")
}

val architectureFromTarget: (KonanTarget, String) -> String by extra { target, iosDependency ->
    val isSimulatorTarget: Boolean = target is KonanTarget.IOS_X64 || target is KonanTarget.IOS_SIMULATOR_ARM64
    when {
        iOs64BitOnlyCpuDependencies.contains(iosDependency) && isSimulatorTarget -> "ios-arm64_x86_64-simulator"
        !iOs64BitOnlyCpuDependencies.contains(iosDependency) && isSimulatorTarget -> "ios-arm64_i386_x86_64-simulator"
        iOs64BitOnlyCpuDependencies.contains(iosDependency) && !isSimulatorTarget -> "ios-arm64"
        else -> "ios-arm64_armv7"
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

subprojects {
    tasks {
        if (projectDir.resolve("$cinteropDir/Cartfile").exists()) { // skipping firebase-common module
            listOf("bootstrap", "update").forEach { command ->
                /*
                * Due to a bug in Carthage we are facing a randomly race condition failure
                * during carthage bootstrap so we can't use the normal one time carthage bootstrap
                * command but we need to it for each of the dependency separately.
                * Issue: https://github.com/Carthage/Carthage/issues/2777
                * Solution: https://github.com/Carthage/Carthage/issues/2777#issuecomment-572896908
                */
                task("carthage${command.capitalize()}") {
                    group = "carthage"
                    doLast {
                        iosCartfileDependencies.forEach { dependency ->
                            exec {
                                workingDir = projectDir.resolve(cinteropDir)
                                executable = "carthage"
                                args(
                                    command,
                                    "--platform", "iOS",
                                    "--use-xcframeworks",
                                    "--cache-builds",
                                    dependency
                                )
                            }
                        }
                    }
                }
            }
        }

        if (Os.isFamily(Os.FAMILY_MAC)) {
            withType(CInteropProcess::class) {
                dependsOn("carthageBootstrap")
            }
        }

        create("carthageClean", Delete::class.java) {
            group = "carthage"
            delete(
                projectDir.resolve("$cinteropDir/Carthage"),
                projectDir.resolve("$cinteropDir/Cartfile.resolved")
            )
        }
    }

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class) {
        kotlinOptions {
            freeCompilerArgs += "-Xjvm-default=all"
        }
    }
}
