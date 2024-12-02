plugins {
    `java-library`

    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.blossom)
}

val minecraftVersion = rootProject.libs.versions.minecraft.get()
val fabricLoaderVersion = rootProject.libs.versions.fabric.loader.get()

repositories {
    maven("https://jitpack.io")
    maven("https://maven.wispforest.io")
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

loom {
    splitEnvironmentSourceSets()

    accessWidenerPath.set(file("src/main/resources/circussmp.accesswidener"))
}

dependencies {
    minecraft(libs.minecraft)
    mappings("${libs.yarn.get()}:v2")

    modImplementation(libs.fabric.api)
    modImplementation(libs.fabric.loader)

    modImplementation(libs.owo.lib)
    annotationProcessor(libs.owo.lib)
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

        filesMatching(listOf("fabric.mod.json", "install.properties")) {
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

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(21)
}

tasks {

//    create("github") {
//        dependsOn("remapJar")
//
//        onlyIf {
//            ENV["GITHUB_TOKEN"] != null
//        }
//
//        doLast {
//            val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
//
//            val github = GitHub.connectUsingOAuth(ENV["GITHUB_TOKEN"] as String)
//            val repository = github.getRepository(ENV["GITHUB_REPOSITORY"])
//
//            repository.listReleases().forEach {
//                if (it.tagName == date) {
//                    it.delete()
//                }
//            }
//
//            val releaseBuilder = GHReleaseBuilder(repository, date)
//            releaseBuilder.name("MangoPlex SMP ${project.version}")
//            releaseBuilder.body("New version just came out!")
//            releaseBuilder.commitish("main")
//
//            val ghRelease = releaseBuilder.create()
//            ghRelease.uploadAsset(remapJar.get().archiveFile.get().asFile, "application/java-archive");
//        }
//    }
}
