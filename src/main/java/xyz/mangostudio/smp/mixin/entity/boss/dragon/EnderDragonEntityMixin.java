package xyz.mangostudio.smp.mixin.entity.boss.dragon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

@Mixin(EnderDragonEntity.class)
public class EnderDragonEntityMixin extends MobEntity {
    protected EnderDragonEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * Ender Dragon's max health is increased to 1360.
     */
    @Inject(method = "createEnderDragonAttributes", at = @At(value = "RETURN"), cancellable = true)
    private static void modifyMaxHealth(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.setReturnValue(cir.getReturnValue().add(EntityAttributes.GENERIC_MAX_HEALTH, 1360.0));
    }

    /**
     * Ender Dragon's damage is increased to 15.0.
     */
    @Redirect(method = "damageLivingEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
            )
    )
    private boolean modifyAttackDamage(Entity instance, DamageSource source, float amount) {
        return instance.damage(source, 15.0f);
    }

    /**
     * Ender Dragon is immune to explosions in order to prevent the abuse of beds.
     */
    @Override
    public boolean isImmuneToExplosion() {
        return true;
    }
}
