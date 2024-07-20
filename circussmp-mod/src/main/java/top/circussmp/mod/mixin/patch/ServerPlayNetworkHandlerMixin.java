package top.circussmp.mod.mixin.patch;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.server.network.ServerPlayNetworkHandler;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Unique
    private static final float MAX_SPEED = 3600.0F;

    @ModifyConstant(method = "onPlayerMove", constant = @Constant(floatValue = 100.0f))
    private float modifyMaxPlayerVelocity(float constant) {
        return MAX_SPEED;
    }

    @ModifyConstant(method = "onPlayerMove", constant = @Constant(floatValue = 300.0f))
    private float modifyMaxElytraVelocity(float constant) {
        return MAX_SPEED;
    }

    @ModifyConstant(method = "onVehicleMove", constant = @Constant(doubleValue = 100.0f))
    private double modifyMaxVehicleVelocity(double constant) {
        return MAX_SPEED;
    }
}
