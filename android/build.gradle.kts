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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":cucumber"))
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.ui:ui-tooling:1.4.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
    implementation("androidx.compose.foundation:foundation:1.4.3")
    implementation("androidx.compose.material:material:1.4.3")
    implementation("androidx.activity:activity-compose:1.7.2")

    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.3")
    androidTestImplementation("io.cucumber:cucumber-android:4.10.0")

    androidTestImplementation("io.cucumber:cucumber-java8:7.12.1")
    androidTestImplementation("io.cucumber:cucumber-junit:7.12.1")
    androidTestImplementation(kotlin("test"))
}
