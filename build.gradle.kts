plugins {
    `java-library`

    alias(libs.plugins.fabric.loom)
}

val minecraftVersion = rootProject.libs.versions.minecraft.get()
val fabricLoaderVersion = rootProject.libs.versions.fabric.loader.get()

repositories {
    exclusiveContent {
        forRepository {
            maven("https://api.modrinth.com/maven") {
                name = "Modrinth"
            }
        }
        filter {
            includeGroup("maven.modrinth")
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(21)
}

loom {
    splitEnvironmentSourceSets()

    accessWidenerPath.set(file("src/main/resources/circussmp.accesswidener"))
}

dependencies {
    minecraft(libs.minecraft)
    mappings("${libs.yarn.get()}:v2")

    modImplementation(libs.fabric.api)
    modImplementation(libs.fabric.loader)
}

tasks {
    base {
        archivesName.set("circus-smp")
    }

    processResources {
        inputs.property("version", project.version)
        inputs.property("minecraftVersion", minecraftVersion)
        inputs.property("fabricLoaderVersion", fabricLoaderVersion)

        filteringCharset = "UTF-8"

        filesMatching("fabric.mod.json") {
            expand(mapOf(
                "version" to project.version,
                "minecraftVersion" to minecraftVersion,
                "fabricLoaderVersion" to fabricLoaderVersion,
            ))
        }
    }

    create("fixModSide") {
        file("modpack/mods").listFiles()?.forEach { modFile ->
            run {
                val content = modFile.readText()
                val updatedContent = content.replace("side = \"server\"", "side = \"both\"")
                modFile.writeText(updatedContent)
            }
        }

        doLast {
            println("Please use 'packwiz refresh' to apply the changes.")
        }
    }
}
