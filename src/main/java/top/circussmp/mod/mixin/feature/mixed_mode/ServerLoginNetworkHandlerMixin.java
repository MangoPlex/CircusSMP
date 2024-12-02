package top.circussmp.mod.mixin.feature.mixed_mode;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.circussmp.mod.CircusSMP;

@Mixin(ServerLoginNetworkHandler.class)
public class ServerLoginNetworkHandlerMixin {
    @Redirect(method = "onHello",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/MinecraftServer;isOnlineMode()Z"
            )
    )
    private boolean hackyOnlineMode(@NotNull MinecraftServer instance, LoginHelloC2SPacket packet) {
        return instance.isOnlineMode() || CircusSMP.OFFLINE_PLAYERS.contains(packet.name());
    }
}
