pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("multiplatform") version "2.1.21"
        kotlin("plugin.serialization") version "2.1.21"
        id("com.android.library") version "8.7.2"
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "centrifugo-kotlin"
