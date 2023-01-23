plugins {
    id("com.github.johnrengelman.shadow")
}

dependencies {
    implementation("net.fabricmc:fabric-installer:0.11.1")
}

tasks {
    shadowJar {
        manifest {
            attributes(
                "Implementation-Title" to "SMP Launcher",
                "Implementation-Version" to project.version,
                "Main-Class" to "xyz.mangostudio.smp.launcher.Launcher",
            )
        }

        minimize()
    }

    build {
        dependsOn(shadowJar)
    }
}
