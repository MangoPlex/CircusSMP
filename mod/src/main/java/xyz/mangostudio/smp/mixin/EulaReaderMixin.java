package xyz.mangostudio.smp.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.server.dedicated.EulaReader;

@Mixin(EulaReader.class)
public class EulaReaderMixin {
    /**
     * @author JustMango
     * @reason Skip EULA check
     */
    @Overwrite
    private boolean checkEulaAgreement() {
        return true;
    }
}
