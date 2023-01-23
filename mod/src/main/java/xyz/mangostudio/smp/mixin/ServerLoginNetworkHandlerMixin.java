package xyz.mangostudio.smp.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.mangostudio.smp.mod.BootstrapMod;

import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginNetworkHandler;

@Mixin(ServerLoginNetworkHandler.class)
public class ServerLoginNetworkHandlerMixin {
    @Redirect(method = "onHello",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/MinecraftServer;isOnlineMode()Z"
            )
    )
    private boolean hackyOnlineMode(MinecraftServer instance, LoginHelloC2SPacket packet) {
        return instance.isOnlineMode() && !BootstrapMod.BYPASS_ONLINE_MODE.contains(packet.name());
    }
}
