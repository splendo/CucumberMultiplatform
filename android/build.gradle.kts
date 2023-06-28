plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.corrado4eyes.cucumberplayground.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.corrado4eyes.cucumberplayground.android"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testApplicationId = "com.corrado4eyes.cucumberplayground.test"
        testInstrumentationRunner = "com.corrado4eyes.cucumberplayground.test.CucumberTests"
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
    implementation(project(":cucumber"))
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.ui:ui-tooling:1.4.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
    implementation("androidx.compose.foundation:foundation:1.4.3")
    implementation("androidx.compose.material:material:1.4.3")
    implementation("androidx.activity:activity-compose:1.7.2")

    androidTestImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.3")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$1.4.3")

    implementation("com.splendo.kaluga:architecture-compose:$kalugaVersion")
    implementation("com.splendo.kaluga:resources-compose:$kalugaVersion")
    implementation("com.splendo.kaluga:keyboard-compose:$kalugaVersion")

    androidTestImplementation("io.cucumber:cucumber-core:4.8.1")
    androidTestImplementation("io.cucumber:cucumber-android:4.9.0")
    androidTestImplementation("io.cucumber:cucumber-java8:4.8.1")
    androidTestImplementation("io.cucumber:cucumber-java:4.8.1")
    androidTestImplementation("io.cucumber:cucumber-junit:4.8.1")
    androidTestImplementation(kotlin("test"))
}