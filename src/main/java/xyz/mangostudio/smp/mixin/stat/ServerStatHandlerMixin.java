package xyz.mangostudio.smp.mixin.stat;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.stat.ServerStatHandler;

@Mixin(ServerStatHandler.class)
public class ServerStatHandlerMixin {
    @Inject(method = "save", at = @At("HEAD"), cancellable = true)
    private void stat$disableSaving(CallbackInfo ci) {
        ci.cancel();
    }

    /**
     * @author Spigot (md_5)
     */
    @Inject(method = "setStat", at = @At("HEAD"), cancellable = true)
    private void stat$disableHandler(CallbackInfo ci) {
        ci.cancel();
    }
}
