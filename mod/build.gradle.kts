plugins {
    id("fabric-loom")
}

val minecraftVersion: String by project

loom {
    splitEnvironmentSourceSets()
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:${property("yarnMappings")}:v2")

    modImplementation("net.fabricmc:fabric-loader:${property("loaderVersion")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabricVersion")}")
}
