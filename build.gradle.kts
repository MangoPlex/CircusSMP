import org.kohsuke.github.GHReleaseBuilder
import org.kohsuke.github.GitHub

buildscript {
    dependencies {
        classpath("org.kohsuke:github-api:1.135")
    }
}

plugins {
    java
    id("fabric-loom")
}

val ENV = System.getenv()

val loaderVersion: String by project
val fabricVersion: String by project

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(17)
}

sourceSets {
    main {
        resources {
            srcDir("src/main/docker")
        }
    }
}

loom {
    serverOnlyMinecraftJar()

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

        filesMatching(listOf("fabric.mod.json", "docker-compose.yml")) {
            expand(mapOf(
                "version" to project.version,
                "minecraftVersion" to project.version,
                "loaderVersion" to loaderVersion
            ))
        }
    }

    remapJar {
        archiveFileName.set("mangoplex-smp-mc${project.version}.jar")
    }

    create("github") {
        dependsOn("remapJar")

        onlyIf {
            ENV["GITHUB_TOKEN"] != null
        }

        doLast {
            val github = GitHub.connectUsingOAuth(ENV["GITHUB_TOKEN"] as String)
            val repository = github.getRepository(ENV["GITHUB_REPOSITORY"])

            val releaseBuilder = GHReleaseBuilder(repository, "latest")
            releaseBuilder.name("[$project.minecraft_version] Fabric API $project.version")
            releaseBuilder.body(ENV["CHANGELOG"] ?: "No changelog provided")
            releaseBuilder.commitish("main")

            val ghRelease = releaseBuilder.create()
            ghRelease.uploadAsset(remapJar.get().archiveFile.get().asFile, "application/java-archive");
            ghRelease.uploadAsset(buildDir.resolve("build/resources/main/docker-compose.yml"), "application/x-yaml");
        }
    }
}
