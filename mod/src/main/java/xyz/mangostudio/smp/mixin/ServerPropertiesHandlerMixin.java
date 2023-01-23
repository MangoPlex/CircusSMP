package xyz.mangostudio.smp.mixin;

import java.util.Properties;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.dedicated.AbstractPropertiesHandler;
import net.minecraft.server.dedicated.ServerPropertiesHandler;
import net.minecraft.world.Difficulty;

@Mixin(ServerPropertiesHandler.class)
public abstract class ServerPropertiesHandlerMixin extends AbstractPropertiesHandler<ServerPropertiesHandler> {
    @Mutable
    @Shadow @Final public boolean allowFlight;

    public ServerPropertiesHandlerMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/dedicated/ServerPropertiesHandler;get(Ljava/lang/String;Ljava/util/function/Function;Ljava/lang/Object;)Ljava/lang/Object;",
                    ordinal = 0
            )
    )
    private void modifyProperties(Properties properties, CallbackInfo ci) {
        this.parseBoolean("sync-chunk-writes", false);
        this.getString("level-type", "terra:overworld/overworld");
        this.get("difficulty", combineParser(Difficulty::byId, Difficulty::byName), Difficulty::getName, Difficulty.HARD);
        this.getInt("simulation-distance", 6);
        this.allowFlight = true;
    }
}
