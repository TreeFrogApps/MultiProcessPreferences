plugins {
    id("com.android.library")
}

android {
    compileSdk = 31
    buildToolsVersion = "32.0.0"

    defaultConfig {
        minSdk = 16
        targetSdk = 31
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.annotation:annotation:1.3.0")
}