package xyz.mangostudio.smp.mod;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.mangostudio.smp.bridge.ServerChunkManagerBridge;

import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.world.GameRules;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.impl.event.lifecycle.LoadedChunksCache;

public class BootstrapMod implements DedicatedServerModInitializer {
    public static final List<String> BYPASS_ONLINE_MODE = List.of(
            "djchip"
    );
    public static final ChunkTicketType<Unit> MOD_CHUNK_TICKET_TYPE = ChunkTicketType.create("mod", (a, b) -> 0);
    private static final Logger LOGGER = LogManager.getLogger("MangoplexSMP");

    @Override
    public void onInitializeServer() {
        ServerWorldEvents.LOAD.register(((server, world) -> {
            server.getGameRules().get(GameRules.DISABLE_ELYTRA_MOVEMENT_CHECK).set(true, server);
            server.getGameRules().get(GameRules.PLAYERS_SLEEPING_PERCENTAGE).set(50, server);
        }));

        ServerLifecycleEvents.SERVER_STARTED.register(((server) -> {
            assert server != null;

            // Config mine-treasure
            server.getCommandManager().executeWithPrefix(server.getCommandSource(), "scoreboard players set in mt.common_chance 2200");
            server.getCommandManager().executeWithPrefix(server.getCommandSource(), "scoreboard players set in mt.rare_chance 11000");
            server.getCommandManager().executeWithPrefix(server.getCommandSource(), "scoreboard players set in mt.epic_chance 30900");
            server.getCommandManager().executeWithPrefix(server.getCommandSource(), "scoreboard players set in mt.legendary_chance 360000");
        }));

        ServerTickEvents.END_SERVER_TICK.register(((server) -> {
            if (server.getTicks() % 20 != 0) return;

            if (server.getPlayerManager().getPlayerList().size() == 0) {
                try {
                    AtomicInteger chunkUnloadedAmount = new AtomicInteger();
                    
                    for (ServerWorld world : server.getWorlds()) {
                        ((LoadedChunksCache) world).fabric_getLoadedChunks().forEach(chunk -> {
                            if (world.getChunkManager().isChunkLoaded(chunk.getPos().x, chunk.getPos().z)) {
                                world.getChunkManager().removeTicket(MOD_CHUNK_TICKET_TYPE, chunk.getPos(), 1, Unit.INSTANCE);
                                ((ServerChunkManagerBridge) world.getChunkManager()).purgeUnload();

                                chunkUnloadedAmount.getAndIncrement();
                            }
                        });
                    }

                    if (chunkUnloadedAmount.get() > 0) {
                        LOGGER.info(String.format("Unloaded %d chunks", chunkUnloadedAmount.get()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }
}
