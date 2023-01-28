import org.kohsuke.github.GHRelease
import org.kohsuke.github.GHReleaseBuilder
import org.kohsuke.github.GitHub
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
            val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))

            val github = GitHub.connectUsingOAuth(ENV["GITHUB_TOKEN"] as String)
            val repository = github.getRepository(ENV["GITHUB_REPOSITORY"])

            repository.listReleases().forEach {
                if (it.tagName == date) {
                    it.delete()
                }
            }

            val releaseBuilder = GHReleaseBuilder(repository, date)
            releaseBuilder.name("MangoPlex SMP ${project.version}")
            releaseBuilder.body("New version just came out!")
            releaseBuilder.commitish("main")

            val ghRelease = releaseBuilder.create()
            ghRelease.uploadAsset(remapJar.get().archiveFile.get().asFile, "application/java-archive");
            ghRelease.uploadAsset(buildDir.resolve("resources/main/docker-compose.yml"), "application/x-yaml");
        }
    }
}
