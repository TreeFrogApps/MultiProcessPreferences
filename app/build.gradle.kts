plugins {
    id("com.android.application")
}

android {
    compileSdk = 31
    buildToolsVersion = "32.0.0"

    defaultConfig {
        applicationId = "com.treefrogapps.multiprocesspreferences"
        versionCode = 1
        versionName = "1.0.0"
        minSdk = 16
        targetSdk = 31
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":multi-preferences"))
    implementation("androidx.appcompat:appcompat:1.4.1")

    testImplementation("junit:junit:4.13.2")}