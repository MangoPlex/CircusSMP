package xyz.mangostudio.smp.mod.patch;

import xyz.mangostudio.smp.mixin.entity.attribute.ClampedEntityAttributeAccessor;
import xyz.mangostudio.smp.mod.SMPMod;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class AttributeModifiers {
    public static void register() {
        fixAttribute("minecraft:generic.max_health", 8192);
    }

    private static void fixAttribute(String name, int maxValue) {
        final Identifier attributeId = Identifier.tryParse(name);
        final EntityAttribute attribute = attributeId != null ? Registries.ATTRIBUTE.get(attributeId) : null;

        if (attribute != null) {
            if (attribute instanceof ClampedEntityAttribute clampedEntityAttribute) {
                ClampedEntityAttributeAccessor accessor = (ClampedEntityAttributeAccessor) clampedEntityAttribute;

                if (maxValue >= 0) accessor.maxValue(maxValue);
            }
        } else {
            SMPMod.LOGGER.error("Attribute {} was not found in the registry!", name);
        }
    }
}
