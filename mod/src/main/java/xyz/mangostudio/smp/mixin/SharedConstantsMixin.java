package xyz.mangostudio.smp.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.SharedConstants;

@Mixin(SharedConstants.class)
public class SharedConstantsMixin {
    /**
     * @author astei
     * @reason Disable data fixer optimization
     */
    @Overwrite
    public static void enableDataFixerOptimization() {
        // NO-OP
    }
}
