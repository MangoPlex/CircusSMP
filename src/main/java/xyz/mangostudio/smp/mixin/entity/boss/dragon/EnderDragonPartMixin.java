package xyz.mangostudio.smp.mixin.entity.boss.dragon;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.world.World;

@Mixin(EnderDragonPart.class)
public abstract class EnderDragonPartMixin extends Entity {
    public EnderDragonPartMixin(EntityType<?> type, World world) {
        super(type, world);
    }
}
