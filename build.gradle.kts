plugins {
    id("com.android.application") version "7.1.2" apply false
    id("com.android.library") version "7.1.2" apply false
}

allprojects {

    repositories {
        google()
        mavenCentral()
    }
}

tasks {
    register("clean", Delete::class.java)
        .configure { delete(rootProject.buildDir) }
}