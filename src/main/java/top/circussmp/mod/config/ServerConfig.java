package top.circussmp.mod.config;

import top.circussmp.mod.CircusSMP;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public final class ServerConfig {
    private static final Path FILE = Path.of("circus-smp.properties");

    private final Properties properties = new Properties();

    public ServerConfig() {
        reload();
    }

    public static ServerConfig init() {
        return new ServerConfig();
    }

    public void reload () {
        try {
            if (Files.notExists(FILE)) {
                Files.createFile(FILE);
                this.properties.setProperty("offline-players", "");
            }

            this.properties.load(new FileInputStream(FILE.toFile()));
        } catch (IOException e) {
            CircusSMP.LOGGER.error("Failed to load circus-smp.properties", e);
        }
    }

    public void save() {
        try {
            this.properties.store(Files.newBufferedWriter(FILE), "Circus-SMP Properties");
        } catch (IOException e) {
            CircusSMP.LOGGER.error("Failed to save circus-smp.properties", e);
        }
    }

    public List<String> getOfflinePlayers() {
        return Arrays.asList(this.properties.getProperty("offline-players", "").split(","));
    }
}
