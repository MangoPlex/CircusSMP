val ENV = System.getenv()

subprojects {
    apply(plugin = "java")

    repositories {
        maven("https://repo.papermc.io/repository/maven-public/") {
            name = "PaperMC"
        }
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
        options.release.set(17)
    }
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
