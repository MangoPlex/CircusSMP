package xyz.mangostudio.smp.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.mangostudio.smp.bridge.ServerChunkManagerBridge;
import xyz.mangostudio.smp.mixin.accessor.ThreadedAnvilChunkStorageAccessor;

import net.minecraft.server.world.ChunkTicketManager;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;

@Mixin(ServerChunkManager.class)
public abstract class ServerChunkManagerMixin implements ServerChunkManagerBridge {
    @Shadow
    @Final
    public ThreadedAnvilChunkStorage threadedAnvilChunkStorage;
    @Shadow
    @Final
    ServerWorld world;
    @Shadow
    @Final
    private ChunkTicketManager ticketManager;

    @Shadow
    abstract boolean tick();

    @Shadow
    protected abstract void initChunkCaches();

    @Override
    public void purgeUnload() {
        this.world.getProfiler().push("purge");
        this.ticketManager.removePersistentTickets();
        this.tick();
        this.world.getProfiler().push("unload");
        ((ThreadedAnvilChunkStorageAccessor) this.threadedAnvilChunkStorage).unloadChunks(() -> true);
        this.initChunkCaches();
    }
}
