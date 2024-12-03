package top.circussmp.mod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.circussmp.mod.config.ServerConfig;

public class CircusSMP implements ModInitializer {
    public static final String MOD_ID = "circussmp";
    public static final Logger LOGGER = LogManager.getLogger("Circus-SMP");

    public static ServerConfig SERVER_CONFIG = ServerConfig.init();

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        SERVER_CONFIG.save();

        ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((server, manager) -> {
            LOGGER.info("Reloading circus-smp.properties");
            SERVER_CONFIG.reload();
        });
    }
}
