plugins {
    id("fabric-loom")
}

val loaderVersion: String by project
val fabricVersion: String by project

loom {
    splitMinecraftJar()

    accessWidenerPath.set(file("src/main/resources/smp.accesswidener"))
}

dependencies {
    minecraft("com.mojang:minecraft:$version")
    mappings("net.fabricmc:yarn:${property("yarnMappings")}:v2")

    modImplementation("net.fabricmc:fabric-loader:${property("loaderVersion")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabricVersion")}")
}

tasks {
    processResources {
        inputs.property("version", project.version)
        inputs.property("minecraftVersion", project.version)
        inputs.property("loaderVersion", loaderVersion)

        filteringCharset = "UTF-8"

        filesMatching(listOf("fabric.mod.json")) {
            expand(mapOf(
                "version" to project.version,
                "minecraftVersion" to project.version,
                "loaderVersion" to loaderVersion
            ))
        }
    }

    remapJar {
        archiveFileName.set("circus-smp-mc${project.version}.jar")
    }
}
