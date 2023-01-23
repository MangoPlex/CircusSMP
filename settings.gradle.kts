pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        gradlePluginPortal()
    }

    plugins {
        id("fabric-loom") version "1.0-SNAPSHOT"
        id("com.github.johnrengelman.shadow") version "7.1.2"
    }
}

rootProject.name = "mangoplex-smp"

include("launcher")
include("mod")
