pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "MultiProcessPreferences"

include(
    ":app",
    ":multi-preferences"
)