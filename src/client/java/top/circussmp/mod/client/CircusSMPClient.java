package top.circussmp.mod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import top.circussmp.mod.CircusSMP;
import top.circussmp.mod.packet.c2s.OfflineVerificationC2SPacket;

public class CircusSMPClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.INIT.register(((networkHandler, client) -> {
            CircusSMP.MAIN_CHANNEL.clientHandle().send(new OfflineVerificationC2SPacket(networkHandler.getProfile(), CircusSMP.COMMON_CONFIG.offlinePassword()));
        }));
    }
}
