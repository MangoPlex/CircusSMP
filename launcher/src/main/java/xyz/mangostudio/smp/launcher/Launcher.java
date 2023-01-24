package xyz.mangostudio.smp.launcher;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import net.fabricmc.installer.ServerLauncher;
import net.fabricmc.installer.util.Utils;

public class Launcher {
    public static void main(String[] args) throws Throwable {
        Path baseDir = Paths.get(".").toAbsolutePath().normalize();
        Path modDir = baseDir.resolve("mods");
        Properties installProperties = LauncherUtil.hijackFromInstaller();

        String minecraftVersion = installProperties.getProperty("game-version");
        String fabricVersion = installProperties.getProperty("fabric-api-version");

        Path fabricApiJar = modDir.resolve(String.format("fabric-api-%s.jar", fabricVersion));

        if (Files.notExists(fabricApiJar)) {
            Utils.downloadFile(new URL("https://maven.fabricmc.net/net/fabricmc/fabric-api/fabric-api/@ver@/fabric-api-@ver@.jar".replace("@ver@", fabricVersion)), fabricApiJar);
        }

        new ModUpdater(modDir, minecraftVersion,
                "alternate-current:latest",
                "c2me-fabric:latest",
                "chunky:latest",
                "ferrite-core:latest",
                "krypton:latest",
                "lithium:latest",
                "memoryleakfix:latest",
                "servercore:latest",
                "starlight:latest",
                "vmp-fabric:latest",
                "spark:latest",

                "no-chat-reports:latest",

                "terra:latest",
                "fallingtree:latest",

                "mine-treasure:9eYz1rB7",
                "mob-captains:fykjkwS6",
                "explorify:EWas9ozb",
                "apollos-additional-structures:vjINgk56"
        ).update();

        ServerLauncher.main(args);
    }
}
