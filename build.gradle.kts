plugins {
    java
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
    }
}

val minecraftVersion: String by project
val loaderVersion: String by project
val fabricVersion: String by project

subprojects {
    apply(plugin = "java")

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    tasks {
        processResources {
            inputs.property("version", project.version)
            inputs.property("minecraftVersion", minecraftVersion)
            inputs.property("loaderVersion", loaderVersion)
            inputs.property("fabricVersion", fabricVersion)

            filteringCharset = "UTF-8"

            filesMatching(listOf("fabric.mod.json", "install.properties")) {
                expand(mapOf(
                    "version" to project.version,
                    "minecraftVersion" to minecraftVersion,
                    "loaderVersion" to loaderVersion,
                    "fabricVersion" to fabricVersion
                ))
            }
        }
    }
}
