package top.circussmp.mod;

import io.wispforest.owo.network.OwoNetChannel;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.circussmp.mod.config.CommonConfig;
import top.circussmp.mod.packet.c2s.OfflineVerificationC2SPacket;

import java.util.ArrayList;
import java.util.List;

public class CircusSMP implements ModInitializer {
    public static final String MOD_ID = "circussmp";
    public static final Logger LOGGER = LogManager.getLogger("Circus-SMP");

    public static final OwoNetChannel MAIN_CHANNEL = OwoNetChannel.create(id("main"));
    public static final CommonConfig COMMON_CONFIG = CommonConfig.createAndLoad();

    public static final List<String> OFFLINE_PLAYERS = new ArrayList<>();

    @Override
    public void onInitialize() {
        MAIN_CHANNEL.registerServerbound(OfflineVerificationC2SPacket.class, (packet, access) -> {
            var playerName = packet.profile().getName();

            if (COMMON_CONFIG.offlinePassword().equals(packet.password()) && COMMON_CONFIG.offlinePlayers().contains(playerName)) {
                OFFLINE_PLAYERS.add(playerName);
            }
        });
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
