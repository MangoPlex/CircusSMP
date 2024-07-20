package top.circussmp.mod.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.attribute.ClampedEntityAttribute;

@Mixin(ClampedEntityAttribute.class)
public interface ClampedEntityAttributeAccessor {
    @Mutable
    @Accessor("minValue")
    void minValue(double value);

    @Mutable
    @Accessor("maxValue")
    void maxValue(double value);
}
