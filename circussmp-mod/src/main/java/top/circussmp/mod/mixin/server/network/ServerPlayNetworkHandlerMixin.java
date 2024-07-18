package top.circussmp.mod.mixin.server.network;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.server.network.ServerPlayNetworkHandler;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
//    @ModifyConstant(method = "onPlayerMove", constant = @Constant(floatValue = 100.0f))
//    private static float modifyMaxPlayerMovement(float constant) {
//        return 360.0f;
//    }
//
//    @ModifyConstant(method = "onVehicleMove", constant = @Constant(doubleValue = 100.0))
//    private static double modifyMaxVehicleMovement(double constant) {
//        return 360.0;
//    }
}
