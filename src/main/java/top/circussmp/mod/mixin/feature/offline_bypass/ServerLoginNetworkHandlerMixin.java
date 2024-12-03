package top.circussmp.mod.mixin.feature.offline_bypass;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.circussmp.mod.CircusSMP;

@Mixin(ServerLoginNetworkHandler.class)
public class ServerLoginNetworkHandlerMixin {
    @Redirect(method = "onHello",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/ClientConnection;isLocal()Z"
            )
    )
    private boolean hackyOnlineMode(@NotNull ClientConnection connection, LoginHelloC2SPacket packet) {
        return connection.isLocal() || CircusSMP.SERVER_CONFIG.getOfflinePlayers().contains(packet.name());
    }
}
