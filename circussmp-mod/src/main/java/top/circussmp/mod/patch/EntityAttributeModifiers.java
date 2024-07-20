package top.circussmp.mod.patch;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.entry.RegistryEntry;
import top.circussmp.mod.mixin.accessor.ClampedEntityAttributeAccessor;
import top.circussmp.mod.SMPMod;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class EntityAttributeModifiers {
    public static void register() {
        modify(EntityAttributes.GENERIC_MAX_HEALTH, 36_000D);
        modify(EntityAttributes.GENERIC_ARMOR, 36_000D);
        modify(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 36_000D);
        modify(EntityAttributes.GENERIC_ATTACK_DAMAGE, 36_000D);
    }

    private static void modify(RegistryEntry<EntityAttribute> entry, double maxValue) {
        final var attribute = entry.value();

        if (attribute != null) {
            if (attribute instanceof ClampedEntityAttribute clampedEntityAttribute) {
                ClampedEntityAttributeAccessor accessor = (ClampedEntityAttributeAccessor) clampedEntityAttribute;

                if (maxValue >= 0) accessor.maxValue(maxValue);
            }
        } else {
            SMPMod.LOGGER.error("Attribute {} was not found in the registry!", entry.getIdAsString());
        }
    }
}
