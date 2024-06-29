package xyz.mangostudio.smp.mod;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.mangostudio.smp.bridge.MinecraftServerBridge;
import xyz.mangostudio.smp.mod.patch.AttributeModifiers;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class SMPMod implements DedicatedServerModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("MangoplexSMP");

    @Override
    public void onInitializeServer() {
        Properties properties = new Properties();

        try {
            Path path = Path.of("smp.properties");

            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            properties.load(new FileInputStream(path.toFile()));
        } catch (Exception e) {
            LOGGER.error("Failed to load smp.properties", e);
        }

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            ((MinecraftServerBridge) server).setWhitelistNames(List.of(properties.getProperty("offline_users", "").split(",")));
        });

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {

        });

        AttributeModifiers.register();
    }
}
