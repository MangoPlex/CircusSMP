package xyz.mangostudio.smp.mixin.accessor;

import java.util.function.BooleanSupplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.server.world.ThreadedAnvilChunkStorage;

@Mixin(ThreadedAnvilChunkStorage.class)
public interface ThreadedAnvilChunkStorageAccessor {
    @Invoker("unloadChunks")
    void unloadChunks(BooleanSupplier shouldKeepTicking);
}
