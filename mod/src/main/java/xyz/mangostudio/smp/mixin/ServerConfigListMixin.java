package xyz.mangostudio.smp.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.BannedIpList;
import net.minecraft.server.BannedPlayerList;
import net.minecraft.server.OperatorList;
import net.minecraft.server.ServerConfigList;

@Mixin(ServerConfigList.class)
public class ServerConfigListMixin {
    @Inject(method = "save", at = @At("HEAD"), cancellable = true)
    private void toggleSaving(CallbackInfo ci) {
        ServerConfigList instance = (ServerConfigList) (Object) this;

        if (instance instanceof BannedIpList || instance instanceof BannedPlayerList || instance instanceof OperatorList) {
            ci.cancel();
        }
    }
}
