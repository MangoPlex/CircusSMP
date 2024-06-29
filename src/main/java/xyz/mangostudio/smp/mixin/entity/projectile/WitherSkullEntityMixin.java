package xyz.mangostudio.smp.mixin.entity.projectile;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.world.World;

@Mixin(WitherSkullEntity.class)
public class WitherSkullEntityMixin extends ExplosiveProjectileEntity {
    protected WitherSkullEntityMixin(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
}
