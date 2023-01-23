package xyz.mangostudio.smp.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerChunkManager;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Redirect(method = "prepareStartRegion", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerChunkManager;getTotalChunksLoadedCount()I"))
    public int onPrepareStartReg_redirectChunksLoaded(ServerChunkManager scm) {
        return 441;
    }
}
