import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.file.File
import org.jetbrains.kotlin.konan.properties.Properties
import org.jetbrains.kotlin.konan.properties.hasProperty
import org.jetbrains.kotlin.konan.properties.loadProperties

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

    val target: KotlinNativeTarget.() -> Unit = {
        binaries {
            framework {
                transitiveExport = true
                export(project(":shared"))
                export(project(":cucumber"))
                baseName = "shared"

                linkFrameworkSearchPaths("$projectDir/../cucumber")

                getTest("DEBUG").apply {
                    linkFrameworkSearchPaths("$projectDir/../cucumber")
                }
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
    compileOptions {
        sourceCompatibility =  JavaVersion.VERSION_11
        targetCompatibility =  JavaVersion.VERSION_11
    }
}

val KotlinNativeTarget.targetType: String get() {
    val isSimulatorTarget: Boolean =
        konanTarget is org.jetbrains.kotlin.konan.target.KonanTarget.IOS_X64 || konanTarget is org.jetbrains.kotlin.konan.target.KonanTarget.IOS_SIMULATOR_ARM64
    return if (isSimulatorTarget) "iphonesimulator" else "iphoneos"
}

fun Properties.getPropertyOrNull(key: String) = if (hasProperty(key)) getProperty(key) else null
fun Properties.getListProperty(key: String) = getPropertyOrNull(key).orEmpty().split("(\" )?\"".toRegex()).filter { it.isNotEmpty() }

fun Properties.getFrameworkSearchPaths() = (getListProperty("FRAMEWORK_SEARCH_PATHS") + getPropertyOrNull("CONFIGURATION_BUILD_DIR")).filterNotNull()

/**
 * Gets the [Properties] file of a [KotlinNativeTarget] for a given dependency loaded by the `dependencies` module
 * @param dependency the name of the dependency to get the properties from
 * @param pathToIosDependencies the absolute path to the root folder of the health-ios-dependencies module
 * @return the [Properties] file of the dependency
 */
fun KotlinNativeTarget.getPropertiesForDependency(dependency: String, pathToIosDependencies: String): Properties {
    val path = "$pathToIosDependencies/build/cocoapods/buildSettings/build-settings-$targetType-$dependency.properties"
    val file = org.jetbrains.kotlin.konan.file.File(path)
    return file.loadProperties()
}

/**
 * Load a list of all dependencies loaded by the `dependencies` module are added to a `NativeBinary`
 * This checks the `defs` folder to determine these dependencies
 * @param pathToIosDependencies the absolute path to the root folder of the health-ios-dependencies module
 * @param includeDependency returns whether a dependency with a given name should be returned
 * @return the list of dependencies to be added
 */
fun Project.dependenciesFromDefs(pathToIosDependencies: String, includeDependency: (String) -> Boolean = { true }): List<String> {
    // Load all .def files from the defs folder to determine the list of dependencies
    val defs = File("$pathToIosDependencies/build/cocoapods/defs")
    return defs.listFilesOrEmpty.mapNotNull { dependency ->
        val fileName = dependency.name.removeSuffix(".${dependency.extension}")
        when {
            dependency.extension != "def" -> null
            includeDependency(fileName) -> fileName
            else -> null
        }
    }
}

/**
 * Creates the framework search paths required to add all dependencies loaded by the `dependencies` module
 * @param pathToIosDependencies the absolute path to the root folder of the health-ios-dependencies module
 * @param includeDependency returns whether a dependency with a given name should be returned
 * @return the set of paths to add to the framework search paths
 */
fun KotlinNativeTarget.createFrameworkSearchPath(pathToIosDependencies: String, includeDependency: (String) -> Boolean = { true }): Set<String> {
    val dependencies = dependenciesFromDefs(pathToIosDependencies, includeDependency)
    return dependencies.map { dependency ->
        val properties = getPropertiesForDependency(dependency, pathToIosDependencies)
        properties.getFrameworkSearchPaths()
    }.flatten().toSet()
}

/**
 * Ensures that all dependencies loaded by the `dependencies` module are added to a `NativeBinary`
 * @param pathToIosDependencies the absolute path to the root folder of the health-ios-dependencies module
 * @param includeDependency returns whether a dependency with a given name should be added to the `NativeBinary`.
 */
fun org.jetbrains.kotlin.gradle.plugin.mpp.NativeBinary.linkFrameworkSearchPaths(pathToIosDependencies: String, includeDependency: (String) -> Boolean = { true }) {
    val frameworkSearchPaths = target.createFrameworkSearchPath(pathToIosDependencies, includeDependency)
    // Add all framework search paths
    linkerOpts(frameworkSearchPaths.map { "-F$it" })

    // Add all frameworks specified by the framework search paths
    dependenciesFromDefs(pathToIosDependencies, includeDependency).forEach { dependency ->
        val frameworkFileExists = frameworkSearchPaths.any { dir -> org.jetbrains.kotlin.konan.file.File(
            "$dir/$dependency.framework"
        ).exists }
        if (frameworkFileExists) linkerOpts("-framework", dependency)
    }
    // For executable we should set the rpath so the framework is included in the build
    if (this is org.jetbrains.kotlin.gradle.plugin.mpp.AbstractExecutable) {
        frameworkSearchPaths.forEach {
            linkerOpts("-rpath", it)
        }
    }
}