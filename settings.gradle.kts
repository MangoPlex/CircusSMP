pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        gradlePluginPortal()
    }

    plugins {
        id("fabric-loom") version "1.0-SNAPSHOT"
    }
}

rootProject.name = "mangoplex-smp"
