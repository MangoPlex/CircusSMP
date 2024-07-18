package top.circussmp.proxy;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SMPConfig {
    private final SMPProxy plugin;
    private final Properties props = new Properties();

    public SMPConfig(SMPProxy plugin) {
        this.plugin = plugin;

        try {
            Path path = Path.of("circus-smp.properties");

            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            this.props.load(new FileInputStream(path.toFile()));
        } catch (Exception e) {
            plugin.getLogger().error("Failed to load circus-smp.properties", e);
        }
    }

    public List<String> getAllowedOfflineUsernames() {
        return Arrays.stream(this.props.getProperty("allowed-offline-usernames", "").trim().split(",")).toList();
    }
}
