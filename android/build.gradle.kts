plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.splendo.cucumberplayground.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.splendo.cucumberplayground.android"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testApplicationId = "com.splendo.cucumberplayground.test"
        testInstrumentationRunner = "com.splendo.cucumberplayground.test.CucumberTests"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = freeCompilerArgs + "-Xjvm-default=all"
    }
}

dependencies {
    val kalugaVersion: String by project

    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.5.3")
    implementation("androidx.compose.ui:ui-tooling:1.5.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.3")
    implementation("androidx.compose.foundation:foundation:1.5.3")
    implementation("androidx.compose.material:material:1.5.3")
    implementation("androidx.activity:activity-compose:1.8.0")

    androidTestImplementation(kotlin("test"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.3")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.3")

    implementation("com.splendo.kaluga:architecture-compose:$kalugaVersion")
    implementation("com.splendo.kaluga:resources-compose:$kalugaVersion")
    implementation("com.splendo.kaluga:keyboard-compose:$kalugaVersion")

    androidTestImplementation(project(":cucumberShared"))
    androidTestImplementation("io.cucumber:cucumber-android:4.10.0")
    // TODO figure out how it can be updated without breeaking the project
    androidTestImplementation("io.cucumber:cucumber-java8:4.8.1")

    implementation("io.insert-koin:koin-androidx-compose:3.4.4")
}