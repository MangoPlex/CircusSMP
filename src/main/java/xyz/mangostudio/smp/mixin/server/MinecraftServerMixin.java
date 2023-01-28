package xyz.mangostudio.smp.mixin.server;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.mangostudio.smp.bridge.MinecraftServerBridge;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerChunkManager;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin implements MinecraftServerBridge {
    private List<String> whitelistNames;

    @Redirect(method = "prepareStartRegion", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerChunkManager;getTotalChunksLoadedCount()I"))
    public int onPrepareStartReg_redirectChunksLoaded(ServerChunkManager scm) {
        return 441;
    }

    @Override
    public List<String> getWhitelistNames() {
        return this.whitelistNames;
    }

    @Override
    public void setWhitelistNames(List<String> whitelistNames) {
        this.whitelistNames = whitelistNames;
    }
}
